package com.sptrainer.grouptrainerservice.service;

import com.sptrainer.domain.model.Calification;
import com.sptrainer.grouptrainerservice.repository.ICalificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificateService {

    @Autowired
    private ICalificate iCalificate;

    public List<Calification> findByGroupIdsOrUserIds(List<Long> iduser, List<Long> idgroup,List<Long> idusercalificate){
        return iCalificate.findByGroupIdsOrUserIds(iduser,idgroup,idusercalificate);
    }

    public List<Calification> getAVGforUserIds(List<Long> iduser){
        return iCalificate.getAVGforUserIds(iduser);
    }

    public List<Calification> getQualificationByUserIdsAndGroupIdsAndGeneral(List<Long> idsUser, List<Long> idsGroup){
        return iCalificate.getQualificationByUserIdsAndGroupIdsAndGeneral(idsUser,idsGroup);
    }

    public Calification saveOrUpdate(Calification calification){
        if(calification.getId()==null){
            return iCalificate.save(calification);
        }else{
            iCalificate.update(calification);
            return calification;
        }
    }

}
