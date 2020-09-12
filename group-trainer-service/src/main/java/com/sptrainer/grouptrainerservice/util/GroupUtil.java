package com.sptrainer.grouptrainerservice.util;

import com.sptrainer.domain.model.*;
import com.sptrainer.grouptrainerservice.service.SesionTrainerService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GroupUtil {

    public static GroupTrainer setGroupWithSession(GroupTrainer t, List<SesionTrainer> list, double[] coordinate, SesionTrainerService sesionTrainerService) {
        list = list.stream().filter(x -> x.getIdgroup().equals(t.getId())).collect(Collectors.toList());
        list = list.parallelStream().map(x -> sesionTrainerService.setDistance(coordinate, x)).collect(Collectors.toList());
        List<SesionTrainer> newList = list.parallelStream().map(x -> x).collect(Collectors.toList());
        newList.sort(Comparator.comparing(SesionTrainer::getDistance));
        if(!list.isEmpty()){
            SesionTrainer sm = newList.get(0);
            t.setDistance(sm.getDistance());
        }
        t.setSessions(list);
        return t;
    }

    public static GroupTrainer setGroupWithSession(GroupTrainer t, List<SesionTrainer> list) {
        list = list.stream().filter(x -> x.getIdgroup().equals(t.getId())).collect(Collectors.toList());
        t.setSessions(list);
        return t;
    }

    public static GroupTrainer setGroupWithMember(GroupTrainer t, List<Member> list) {
        list = list.stream().filter(x -> x.getIdgroup().equals(t.getId())).collect(Collectors.toList());
        t.setMembers(list);
        t.setQuantity_member(list.size());
        return t;
    }

    public static GroupTrainer setGroupWithRequestCreated(GroupTrainer t, List<RegistrationRequest> list) {
        list = list.stream().filter(x -> x.getIdgroup().equals(t.getId())).collect(Collectors.toList());
        t.setRequests(list);
        t.setQuantity_solicitude_pending(list.size());
        return t;
    }

    public static GroupTrainer setGroupWithUser(GroupTrainer t, List<User> list) {
        User user = list.stream().filter(x -> x.getId().equals(t.getIduser())).findAny().get();
        t.setUser(user);
        return t;
    }

    public static GroupTrainer setGroupWithRegistrationRequest(GroupTrainer t, List<RegistrationRequest> list) {
        list = list.stream().filter(x -> x.getIdgroup().equals(t.getId())).collect(Collectors.toList());
        t.setRequests(list);
        return t;
    }

    public static GroupTrainer defineStateGroupUserConsulting(GroupTrainer t, Long iduser) {
        Optional<RegistrationRequest> r = t.getRequests().stream().filter(x -> x.getIduser().equals(iduser)).findAny();
        t.setState_user_consulting("UNDEFINED");
        if (r.isPresent()) {
            if (r.get().getState().equals("CREATED")) t.setState_user_consulting("SOLICITUD_PENDING");
            if (r.get().getState().equals("ACCEPT")) t.setState_user_consulting("SOLICITUD_APROVED");
            if (r.get().getState().equals("REJECTED")) t.setState_user_consulting("SOLICITUD_REJECTED");
        }
        return t;
    }

    public static GroupTrainer setGroupNSolicitude(GroupTrainer t, List<RegistrationRequest> list) {
        Optional<RegistrationRequest> r = list.stream().filter(x -> x.getIdgroup().equals(t.getId())).findAny();
        t.setQuantity_solicitude_pending((r.isPresent()) ? r.get().getN() : 0);
        return t;
    }

    public static GroupTrainer setGroupNMember(GroupTrainer t, List<Member> list) {
        Optional<Member> m = list.stream().filter(x -> x.getIdgroup().equals(t.getId())).findAny();
        t.setQuantity_member((m.isPresent()) ? m.get().getN() : 0);
        return t;
    }

    public static RegistrationRequest setRequestWithUser(RegistrationRequest r, List<User> list) {
        User user = list.stream().filter(x -> x.getId().equals(r.getIduser())).findAny().get();
        r.setUser(user);
        return r;
    }

    public static Member setMemberWithUser(Member m, List<User> list) {
        User user = list.stream().filter(x -> x.getId().equals(m.getIduser())).findAny().get();
        m.setUser(user);
        return m;
    }

    public static Member setMemberWithCalificacion(Member m, List<Calification> list) {
        Optional<Calification> cal = list.stream().filter(x -> x.getIduser().equals(m.getIduser())).findAny();
        if(cal.isPresent()){
            m.setCalification(cal.get());
        }else{
            Calification cali=new Calification();
            cali.setIduser(m.getIduser());
            cali.setIdgroup(m.getIdgroup());
            cali.setScore(0L);
            m.setCalification(new Calification(m.getIduser(),m.getIdgroup(),0L,m.getGrouptrainer().getIduser()));
        }
        return m;
    }

    public static Member setMemberWithQualificationComplete(Member m, List<Calification> list) {
        Optional<Calification> cal = list.stream().filter(x -> x.getIduser().equals(m.getIduser())).findAny();
        if(cal.isPresent()){
            m.setCalification(cal.get());
        }else{
            Calification cali=new Calification();
            cali.setIduser(m.getIduser());
            cali.setIdgroup(m.getIdgroup());
            cali.setScore(0L);
            cali.setIdusercalificate(m.getGrouptrainer().getIduser());
            cali.setScoretotal(0.0);
            m.setCalification(cali);
        }
        return m;
    }

    public static Member setMemberWithGroup(Member m,GroupTrainer g){
        m.setGrouptrainer(g);
        return m;
    }

    public static Member setMemberWithCalificationTotal(Member m,List<Calification> list){
        Optional<Calification> cal=list.stream().filter(x->x.getIduser().equals(m.getIduser())).findAny();
        if(cal.isPresent()){
           m.setCalification(cal.get());
        }else{
            m.setCalification(new Calification(m.getIduser(),0.0));
        }
        return m;
    }

    public static GroupTrainer setGroupTrainerWithCalificationTotal(GroupTrainer g,List<Calification> list){
        Optional<Calification> cal=list.stream().filter(x->x.getIduser().equals(g.getIduser())).findAny();
        if(cal.isPresent()){
            g.setCalificationuser(cal.get());
        }else{
            g.setCalificationuser(new Calification(g.getIduser(),0.0));
        }
        return g;
    }

    public static RegistrationRequest setRegistrationRequestWithQualificationGeneral(RegistrationRequest r,List<Calification> list){
        Optional<Calification> cal=list.stream().filter(x->x.getIduser().equals(r.getIduser())).findAny();
        if(cal.isPresent()){
            r.getUser().setCalification(cal.get());
        }else{
            r.getUser().setCalification(new Calification(r.getIduser(),0.0));
        }
        return r;
    }




}
