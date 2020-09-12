package com.sptrainer.grouptrainerservice.service;

import com.sptrainer.domain.model.*;
import com.sptrainer.domain.util.GeneralUtil;
import com.sptrainer.grouptrainerservice.notification.NotificationAsync;
import com.sptrainer.grouptrainerservice.repository.IGroup;
import com.sptrainer.grouptrainerservice.util.GroupUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupTrainerService {

    @Autowired
    private IGroup iGroup;

    @Autowired
    private SesionTrainerService sesionTrainerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationRequestService registrationRequestService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CalificateService calificateService;

    @Autowired
    private NotificationAsync notificationAsync;

    private static final Logger log = LoggerFactory.getLogger(GroupTrainerService.class);

    public List<GroupTrainer> findByUserId(Long userId) {
        return iGroup.findByUserId(userId);
    }

    public int getQuotasAvailable(Long id){
        return iGroup.getQuotasAvailable(id);
    }

    public GroupTrainer findById(Long id){
        return iGroup.findById(id);
    }

    public List<GroupTrainer> findByUserIdforClient(Long userId) {
        List<GroupTrainer> list = iGroup.findByUserId(userId);
        list = list.stream().filter(x -> x.getState().equals("PUB")).collect(Collectors.toList());
        List<Long> ids = list.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<RegistrationRequest> requestList = registrationRequestService.getCountByStateAndGroupsIds(ids, "CREATED");
        list = list.parallelStream().map(x -> GroupUtil.setGroupNSolicitude(x, requestList)).collect(Collectors.toList());
        List<Member> memberList = memberService.getCountByActiveAndGroupsIds(ids);
        list = list.parallelStream().map(x -> GroupUtil.setGroupNMember(x, memberList)).collect(Collectors.toList());
        return list;
    }

    public List<GroupTrainer> findByTrainer(Long userId){
        List<User> userList = userService.findAllUser();
        List<GroupTrainer> list = iGroup.findByUserId(userId);
        List<Long> ids = list.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<Member> listMember = memberService.findByGroupIds(ids, "ACTIVE");
        List<SesionTrainer> sessionList = sesionTrainerService.findByGroupIdS(ids);
        List<RegistrationRequest> listRequest = registrationRequestService.findByGroupIdsAndUserIds(ids, new ArrayList<>(), Arrays.asList(new String[]{"CREATED"}));
        List<Long> idsMembers=listMember.stream().map(x->x.getIduser()).collect(Collectors.toList());
        List<Long> idsUserRequest = listRequest.stream().map(x->x.getIduser()).collect(Collectors.toList());
        List<Calification> ratingMembers = calificateService.getQualificationByUserIdsAndGroupIdsAndGeneral(idsMembers,ids);
        List<Calification> ratingUserRequest = calificateService.getAVGforUserIds(idsUserRequest);


        listMember = listMember.parallelStream().map(x -> GroupUtil.setMemberWithUser(x, userList)).collect(Collectors.toList());
        listMember = listMember.parallelStream().map(x->GroupUtil.setMemberWithQualificationComplete(x,ratingMembers)).collect(Collectors.toList());
        listRequest = listRequest.parallelStream().map(x -> GroupUtil.setRequestWithUser(x, userList)).collect(Collectors.toList());
        listRequest = listRequest.parallelStream().map(x-> GroupUtil.setRegistrationRequestWithQualificationGeneral(x,ratingUserRequest)).collect(Collectors.toList());
        List<Member> memberListFinal = listMember;
        List<RegistrationRequest> listRequestListFinal = listRequest;
        list = list.parallelStream().map(x -> GroupUtil.setGroupWithSession(x, sessionList)).collect(Collectors.toList());
        list = list.parallelStream().map(x -> GroupUtil.setGroupWithMember(x, memberListFinal)).collect(Collectors.toList());
        list = list.parallelStream().map(x -> GroupUtil.setGroupWithRequestCreated(x, listRequestListFinal)).collect(Collectors.toList());
        return list;
    }



    public List<GroupTrainer> findByMember(Long userId) {
        return iGroup.findByMember(userId);
    }

    public GroupTrainer findByGroupIdAndUserId(Long userId, Long groupId) {
        return iGroup.findByGroupIdAndUserId(userId, groupId);
    }

    public GroupTrainer save(GroupTrainer groupTrainer) {
        return iGroup.save(groupTrainer);
    }

    public int update(GroupTrainer groupTrainer) {
        return iGroup.update(groupTrainer);
    }

    public List<GroupTrainer> findByMultipleFilter(SearchGroupRequestDTO requestDTO, double[] coordinate, Long iduser) {
        try {
            List<GroupTrainer> list = iGroup.findByMultipleFilter(requestDTO);
            if (list != null && !list.isEmpty()) {
                list = getGroupListInfoComplete(list, coordinate, iduser);
            }
            return list;
        } catch (Exception e) {
            log.error("findByMultipleFilter->" + e.getMessage());
            return new ArrayList<>();
        }
    }

    public MessageResult<Integer> publish(Long idUser, Long idGroup) {
        try {
            GroupTrainer group = findByGroupIdAndUserId(idUser, idGroup);
            if (group.getId() == null || !group.getActive().equals("true"))
                return MessageResult.result(0, "Este grupo no fue encontrado o no se encuentra activo", 409);

            List<SesionTrainer> list = sesionTrainerService.findByGroupId(idGroup);
            if (list == null || list.isEmpty() || list.stream().filter(x -> x.getActive().equals("true")).collect(Collectors.toList()).isEmpty())
                return MessageResult.result(0, "Este grupo debe tener al menos una sesión activa para ser publicado", 409);

            int result = iGroup.publish(idUser, idGroup);
            notificationAsync.generate(new Notification(idGroup,null,"Un nuevo grupo fue creado ("+group.getName()+"), puede encontrarlo en la seccion de busqueda de grupo por si le interesa ","GENERAL",null,null,"CREATED",group.getIduser()));
            return MessageResult.successful(result, "OK");
        } catch (Exception e) {
            return MessageResult.error(null, e.getMessage());
        }
    }

    public GroupTrainer findByIdCompleteInfo(Long id,double[] coordinate,Long iduser){
        GroupTrainer g=iGroup.findById(id);
        List<User> userList = userService.findAllUser();
        List<Calification> calificationsTrainer= calificateService.findByGroupIdsOrUserIds(Arrays.asList(new Long[]{g.getIduser()}),Arrays.asList(new Long[]{id}),Arrays.asList(new Long[]{iduser}));
        /**/
        g=GroupUtil.setGroupWithUser(g, userList);
        g=GroupUtil.setGroupWithSession(g, sesionTrainerService.findByGroupId(id), coordinate, sesionTrainerService);
        List<Member> members=memberService.findByGroupId(id,"ACTIVE");
        members=members.stream().filter(x->x.getState().equals("ACTIVE")).collect(Collectors.toList());
        members=members.parallelStream().map(x->GroupUtil.setMemberWithUser(x,userList)).collect(Collectors.toList());
        if(!members.isEmpty()){
            Member mt=new Member();
            mt.setIdgroup(members.get(0).getIdgroup());
            mt.setIduser(g.getUser().getId());
            mt.setState("TRAINER");
            mt.setUser(g.getUser());
            members.add(0,mt);
        }
        List<Long> idsmembers=members.stream().map(x->x.getIduser()).collect(Collectors.toList());
        List<Calification> calificationsAVG = calificateService.getAVGforUserIds(idsmembers);
        members=members.parallelStream().map(x->GroupUtil.setMemberWithCalificationTotal(x,calificationsAVG)).collect(Collectors.toList());
        g.setMembers(members);
        g.setCalificationuser((!calificationsTrainer.isEmpty())?calificationsTrainer.get(0):new Calification(g.getIduser(),g.getId(),0L,iduser));
        return g;
    }

    public MessageResult<ResponseGroupInitDTO> findByGroupsInit(double[] coordinate, Long iduser_consulting) {
        ResponseGroupInitDTO dtoResponse = new ResponseGroupInitDTO();
        try {

            SearchGroupRequestDTO dto = new SearchGroupRequestDTO();
            List<GroupTrainer> list = iGroup.findByMultipleFilter(dto);
            if (list != null && !list.isEmpty()) {
                list = getGroupListInfoComplete(list, coordinate, iduser_consulting);
                /** Logica para obtener resultados de respuesta */
                List<String> categories = list.parallelStream().map(x -> x.getCategory()).collect(Collectors.toList());
                categories = categories.stream().distinct().collect(Collectors.toList());
                List<User> userWithGroups = list.parallelStream().map(x -> x.getUser()).collect(Collectors.toList());
                userWithGroups = userWithGroups.stream().filter(GeneralUtil.distinctByKey(x -> x.getId())).collect(Collectors.toList());
                List<GroupTrainer> lg = list;
                userWithGroups = userWithGroups.parallelStream().map(x -> GeneralUtil.getUser(x, lg)).collect(Collectors.toList());
                List<CategoryDTO> categoryDTOS = new ArrayList<>();
                for (String c : categories) {
                    CategoryDTO cat = new CategoryDTO();
                    cat.setName(c);
                    cat.setQuantity(GeneralUtil.getQuantityGroupsByCategory(c, list));
                    categoryDTOS.add(cat);
                }
                dtoResponse.setCategories(categoryDTOS);
                dtoResponse.setUsers(userWithGroups);
                list.sort(Comparator.comparing(GroupTrainer::getDistance));
                dtoResponse.setGroups(list);
                return MessageResult.successful(dtoResponse, "OK");
            } else {
                return MessageResult.result(dtoResponse, "No se encontraron resultados", 404);
            }
        } catch (Exception e) {
            log.error("findByMultipleFilter->" + e.getMessage());
            return MessageResult.error(null, e.getMessage());
        }
    }

    public List<GroupTrainer> getGroupListInfoComplete(List<GroupTrainer> list, double[] coordinate, Long iduser) {
        List<Long> ids = list.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<SesionTrainer> sessionList = sesionTrainerService.findByGroupIdS(ids);
        List<User> userList = userService.findAllUser();
        List<RegistrationRequest> requestList = registrationRequestService.findByGroupIdsAndUserIds(ids, Arrays.asList(new Long[]{iduser}), new ArrayList<>());
        List<Member> memberList = memberService.getCountByActiveAndGroupsIds(ids);
        List<Long> iduserstrainer=list.stream().map(x->x.getIduser()).collect(Collectors.toList());
        List<Calification> calificationsAVG = calificateService.getAVGforUserIds(iduserstrainer);
        /** Logica **/
        list = list.parallelStream().map(x -> GroupUtil.setGroupNMember(x, memberList)).collect(Collectors.toList());
        list = list.stream().map(x -> GroupUtil.setGroupWithSession(x, sessionList, coordinate, sesionTrainerService)).collect(Collectors.toList());
        list = list.stream().map(x -> GroupUtil.setGroupWithRegistrationRequest(x, requestList)).collect(Collectors.toList());
        list = list.stream().map(x -> GroupUtil.defineStateGroupUserConsulting(x, iduser)).collect(Collectors.toList());
        list = list.parallelStream().map(x -> GroupUtil.setGroupWithUser(x, userList)).collect(Collectors.toList());
        list = list.parallelStream().map(x->GroupUtil.setGroupTrainerWithCalificationTotal(x,calificationsAVG)).collect(Collectors.toList());
        list.sort(Comparator.comparing(GroupTrainer::getDistance));
        return list;
    }




}
