package com.sptrainer.grouptrainerservice.implement;

import com.sptrainer.domain.model.Member;
import com.sptrainer.domain.model.RegistrationRequest;
import com.sptrainer.domain.util.SQLUtil;
import com.sptrainer.grouptrainerservice.repository.IMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberImplement implements IMember {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Member save(Member member) {
        final String INSERT_SQL = " INSERT INTO `member`  " +
                " (`iduser`,`idgroup`,`state`,`date_save`)" +
                " VALUES (?,?,?,CAST(NOW() AS CHAR)) ";
        jdbcTemplate.update(INSERT_SQL,
                member.getIduser(), member.getIdgroup(),
                member.getState());
        return member;
    }

    @Override
    public List<Member> findByGroupId(Long idgroup) {
        try {
            return jdbcTemplate.query(
                    "select * from member WHERE  idgroup=?",
                    new Object[]{idgroup},
                    (rs, rowNum) ->
                            SQLUtil.getMember(rs));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Member> findByGroupIds(List<Long> groupids, String state) {
        try {
            String SQL = " select * from member WHERE state='" + state + "' AND  idgroup IN " + SQLUtil.SQLINLong(groupids);
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) -> SQLUtil.getMember(rs));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Member> getCountByActiveAndGroupsIds(List<Long> groupids) {
        try {
            String SQL_IN_G = (groupids.isEmpty()) ? "" : " idgroup IN " + SQLUtil.SQLINLong(groupids);
            String W = "";
            if (!SQL_IN_G.isEmpty()) W = " AND " + SQL_IN_G;

            String SQL = " select idgroup,COUNT(iduser) n from member WHERE state='ACTIVE'  " + W;
            SQL += " GROUP BY idgroup ";
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) ->
                            new Member(
                                    rs.getLong("idgroup"),
                                    rs.getInt("n")
                            )
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int updateStateMember(Long idgroup, Long iduser, String state) {
        final String UPDATE_SQL = "update `member` " +
                "set `state` = ?, date_update = CAST(NOW() AS CHAR) " +
                "where  `iduser` = ? AND idgroup = ? ;";
        return jdbcTemplate.update(UPDATE_SQL,
                state, iduser, idgroup);
    }
}
