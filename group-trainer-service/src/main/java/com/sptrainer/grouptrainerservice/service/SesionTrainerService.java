package com.sptrainer.grouptrainerservice.service;

import com.sptrainer.domain.model.MessageResult;
import com.sptrainer.domain.model.SesionTrainer;
import com.sptrainer.domain.util.GeneralUtil;
import com.sptrainer.grouptrainerservice.repository.ISesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SesionTrainerService {

    @Autowired
    private ISesion iSesion;

    public SesionTrainer save(SesionTrainer sesion) {
        return iSesion.save(sesion);
    }

    public int update(SesionTrainer sesion) {
        return iSesion.update(sesion);
    }

    public int delete(Long idsesion, Long idgroup, Long iduser) {
        return iSesion.delete(idsesion, idgroup, iduser);
    }

    public List<SesionTrainer> findByGroupId(Long groupid) {
        return iSesion.findByGroupId(groupid);
    }

    public List<SesionTrainer> findByGroupIdS(List<Long> groupids) {
        return iSesion.findByGroupIdS(groupids);
    }

    public SesionTrainer setDistance(double[] coordinate, SesionTrainer session) {
        MessageResult<double[]> coordinateSession = GeneralUtil.validateCoordinate(session.getCoordinate());
        if (coordinateSession.isResponse()) {
            session.setDistance(GeneralUtil.round(GeneralUtil.distanceCoordinate(coordinate[0], coordinate[1], coordinateSession.getObjeto()[0], coordinateSession.getObjeto()[1]), 2));
        } else {
            session.setDistance(0);
        }
        return session;
    }

}
