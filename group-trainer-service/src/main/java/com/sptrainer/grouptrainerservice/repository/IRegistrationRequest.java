package com.sptrainer.grouptrainerservice.repository;

import com.sptrainer.domain.model.RegistrationRequest;

import java.util.List;

public interface IRegistrationRequest {

    RegistrationRequest save(RegistrationRequest request);

    int response(RegistrationRequest request);

    List<RegistrationRequest> findByGroupIdsAndUserIds(List<Long> groupids, List<Long> usedis, List<String> states);

    List<RegistrationRequest> getCountByStateAndGroupsIds(List<Long> groupids, String state);


}
