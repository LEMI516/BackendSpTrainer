package com.sptrainer.grouptrainerservice.service;

import com.sptrainer.domain.model.*;
import com.sptrainer.grouptrainerservice.notification.NotificationAsync;
import com.sptrainer.grouptrainerservice.repository.IMember;
import com.sptrainer.grouptrainerservice.repository.IRegistrationRequest;
import com.sptrainer.grouptrainerservice.util.GroupUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationRequestService {

    @Autowired
    private IRegistrationRequest iRegistrationRequest;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupTrainerService groupTrainerService;

    @Autowired
    private NotificationAsync notificationAsync;

    @Autowired
    private CalificateService calificateService;

    public RegistrationRequest save(RegistrationRequest registrationRequest) {
        User user=userService.findByUserId(registrationRequest.getIduser());
        GroupTrainer group=groupTrainerService.findById(registrationRequest.getIdgroup());
        RegistrationRequest result=iRegistrationRequest.save(registrationRequest);
        String detail="El usuario "+user.getFirstname()+" "+user.getLastname()+" realizo una solicitud de inscripción al grupo "+group.getName();
        notificationAsync.generate(new Notification(group.getId(),group.getIduser(),detail,"REQUEST",result.getId(),null,"CREATED",registrationRequest.getIduser()));
        return result;
    }

    public List<RegistrationRequest> findByGroupIdsAndUserIds(List<Long> groupids, List<Long> usedis, List<String> states) {
        return iRegistrationRequest.findByGroupIdsAndUserIds(groupids, usedis, states);
    }

    public List<RegistrationRequest> getCountByStateAndGroupsIds(List<Long> groupids, String state) {
        return iRegistrationRequest.getCountByStateAndGroupsIds(groupids, state);
    }

    public int response(RegistrationRequest registrationRequest) {
        int quotasAvailable=groupTrainerService.getQuotasAvailable(registrationRequest.getIdgroup());
        if(registrationRequest.getState().equals("ACCEPT") && quotasAvailable<=0){
            return -5;
        }

        int result = iRegistrationRequest.response(registrationRequest);
        if (registrationRequest.getState().equals("ACCEPT")) {
            Member m = new Member();
            m.setIduser(registrationRequest.getIduser());
            m.setIdgroup(registrationRequest.getIdgroup());
            m.setState("ACTIVE");
            memberService.save(m);
        }
        GroupTrainer group=groupTrainerService.findById(registrationRequest.getIdgroup());
        User user=userService.findByUserId(group.getIduser());
        String detail="El Entrenador "+user.getFirstname()+" "+user.getLastname()+" respondio su solicitud de inscripción al grupo "+group.getName()+", si fue aceptado en la sección de mis grupos encontrara el grupo al cual se inscribio";
        notificationAsync.generate(new Notification(group.getId(),registrationRequest.getIduser(),detail,"REQUEST",registrationRequest.getId(),null,"CREATED",group.getIduser()));
        return result;
    }

    public List<RegistrationRequest> findByGroupIdsAndUserIdsWhitUser(List<Long> groupids, List<Long> usedis, List<String> states) {
        List<RegistrationRequest> list = iRegistrationRequest.findByGroupIdsAndUserIds(groupids, usedis, states);
        List<User> userList = userService.findAllUser();
        List<Long> idsUserRequest = list.stream().map(x->x.getIduser()).collect(Collectors.toList());
        List<Calification> ratingUserRequest = calificateService.getAVGforUserIds(idsUserRequest);
        /** Logica **/
        list = list.parallelStream().map(x -> GroupUtil.setRequestWithUser(x, userList)).collect(Collectors.toList());
        list = list.parallelStream().map(x-> GroupUtil.setRegistrationRequestWithQualificationGeneral(x,ratingUserRequest)).collect(Collectors.toList());
        return list;
    }

}
