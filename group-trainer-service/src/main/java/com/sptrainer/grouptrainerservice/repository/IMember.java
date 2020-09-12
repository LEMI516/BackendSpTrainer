package com.sptrainer.grouptrainerservice.repository;

import com.sptrainer.domain.model.Member;

import java.util.List;

public interface IMember {

    Member save(Member member);

    List<Member> findByGroupId(Long idgroup);

    List<Member> findByGroupIds(List<Long> groupids, String state);

    List<Member> getCountByActiveAndGroupsIds(List<Long> groupids);

    int updateStateMember(Long idgroup, Long iduser, String state);

}
