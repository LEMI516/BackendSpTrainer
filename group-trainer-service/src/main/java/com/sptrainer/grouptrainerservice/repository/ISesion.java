package com.sptrainer.grouptrainerservice.repository;

import com.sptrainer.domain.model.SesionTrainer;

import java.util.List;

public interface ISesion {

    SesionTrainer save(SesionTrainer sesion);

    int update(SesionTrainer sesion);

    int delete(Long idsesion, Long idgroup, Long iduser);

    List<SesionTrainer> findByGroupId(Long groupid);

    List<SesionTrainer> findByGroupIdS(List<Long> groupids);

}
