package com.sptrainer.grouptrainerservice.repository;

import com.sptrainer.domain.model.GroupTrainer;
import com.sptrainer.domain.model.SearchGroupRequestDTO;

import java.util.List;

public interface IGroup {

    List<GroupTrainer> findByUserId(Long userId);

    GroupTrainer findByGroupIdAndUserId(Long userId, Long groupId);

    GroupTrainer save(GroupTrainer groupTrainer);

    int update(GroupTrainer groupTrainer);

    List<GroupTrainer> findByMultipleFilter(SearchGroupRequestDTO requestDTO);

    int publish(Long userId, Long groupId);

    List<GroupTrainer> findByMember(Long iduser);

    GroupTrainer findById(Long id);

    int getQuotasAvailable(Long id);


}
