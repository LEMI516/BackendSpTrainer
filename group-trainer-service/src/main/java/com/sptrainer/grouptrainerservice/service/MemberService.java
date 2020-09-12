package com.sptrainer.grouptrainerservice.service;

import com.sptrainer.domain.model.*;
import com.sptrainer.grouptrainerservice.notification.NotificationAsync;
import com.sptrainer.grouptrainerservice.repository.IMember;
import com.sptrainer.grouptrainerservice.util.GroupUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private IMember iMember;

    @Autowired
    private UserService userService;

    @Autowired
    private CalificateService calificateService;

    @Autowired
    private GroupTrainerService groupTrainerService;

    @Autowired
    private NotificationAsync notificationAsync;

    public Member save(Member member) {
        return iMember.save(member);
    }

    public int update(Long idgroup, Long iduser, String state) {
        if(state.equals("ACTIVE")){
            int quotasAvailable=groupTrainerService.getQuotasAvailable(idgroup);
            if(quotasAvailable<=0){
                return -5;
            }
        }
        sendNotificationMember(idgroup,iduser,state);
        return iMember.updateStateMember(idgroup, iduser, state);
    }

    public void sendNotificationMember(Long idGroup, Long idUser, String state){
        try {
            User user=userService.findByUserId(idUser);
            GroupTrainer group=groupTrainerService.findById(idGroup);
            String messageNotification="";
            Notification notification=new Notification();
            notification.setIdgrup(idGroup);
            notification.setType_notification("MEMBER");
            notification.setState("CREATED");
            if(state.equals("DROPUP")){
                notification.setIduser(group.getIduser());
                notification.setIdusergenerate(idUser);
                messageNotification="El cliente "+user.getFirstname()+" "+user.getLastname()+" ha cancelado su inscripción en el grupo "+group.getName();
            }else{
                notification.setIduser(idUser);
                notification.setIdusergenerate(group.getIduser());
                if(state.equals("INACTIVE"))
                    messageNotification="Su inscripción en el grupo "+group.getName()+" ha sido inactivada";
                else
                    messageNotification="Su inscripción en el grupo "+group.getName()+" ha sido activada";
            }
            notification.setDescription(messageNotification);
            notificationAsync.generate(notification);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Member> findByGroupId(Long idgroup, String state) {
        return iMember.findByGroupId(idgroup);
    }

    public List<Member> findByGroupIdsWhitUser(List<Long> groupIds, String state) {
        GroupTrainer g=groupTrainerService.findById(groupIds.get(0));
        List<Member> list = iMember.findByGroupIds(groupIds, state);
        list=list.parallelStream().map(x->GroupUtil.setMemberWithGroup(x,g)).collect(Collectors.toList());
        List<User> userList = userService.findAllUser();
        List<Long> idsUser=list.stream().map(x->x.getIduser()).collect(Collectors.toList());
        List<Calification> ratingMembers = calificateService.getQualificationByUserIdsAndGroupIdsAndGeneral(idsUser,groupIds);
        /** Logica **/
        list = list.parallelStream().map(x -> GroupUtil.setMemberWithUser(x, userList)).collect(Collectors.toList());
        list = list.parallelStream().map(x -> GroupUtil.setMemberWithQualificationComplete(x, ratingMembers)).collect(Collectors.toList());
        return list;
    }

    public List<Member>  findByGroupIds(List<Long> groupIds, String state){
        return iMember.findByGroupIds(groupIds,state);
    }

    public List<Member> getCountByActiveAndGroupsIds(List<Long> groupids) {
        return iMember.getCountByActiveAndGroupsIds(groupids);
    }


}
