package com.sptrainer.grouptrainerservice.repository;

import com.sptrainer.domain.model.Calification;
import java.util.List;

public interface ICalificate {

    Calification save(Calification calification);

    int update(Calification calification);

    List<Calification> findByGroupIdsOrUserIds(List<Long> iduser,List<Long> idgroup,List<Long> idusercalificate);

    List<Calification> getAVGforUserIds(List<Long> iduser);

    List<Calification> getQualificationByUserIdsAndGroupIdsAndGeneral(List<Long> idsUser,List<Long> idsGroup);
}
