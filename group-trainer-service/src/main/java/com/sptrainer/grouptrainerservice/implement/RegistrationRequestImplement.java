package com.sptrainer.grouptrainerservice.implement;

import com.sptrainer.domain.model.RegistrationRequest;
import com.sptrainer.domain.util.SQLUtil;
import com.sptrainer.grouptrainerservice.repository.IRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRequestImplement implements IRegistrationRequest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RegistrationRequest save(RegistrationRequest request) {
        final String INSERT_SQL = " INSERT INTO `registration_request`  " +
                " (`iduser`,`idgroup`,`state`,`comment`,`dateregistration`)" +
                " VALUES (?,?,?,?,CAST(NOW() AS CHAR)) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                ps = SQLUtil.setDataRegistrationRequest(ps, request);
                return ps;
            }
        }, keyHolder);
        request.setId(keyHolder.getKey().longValue());
        return request;
    }

    @Override
    public int response(RegistrationRequest request) {
        final String UPDATE_SQL = "update `registration_request` " +
                "set `state` = ?,`answer` = ?, dateanswer = CAST(NOW() AS CHAR) " +
                "where `id` = ? AND  `iduser` = ? AND idgroup = ? ;";
        return jdbcTemplate.update(UPDATE_SQL,
                request.getState(), request.getAnswer(),
                request.getId(), request.getIduser(), request.getIdgroup());
    }

    @Override
    public List<RegistrationRequest> findByGroupIdsAndUserIds(List<Long> groupids, List<Long> usedis, List<String> states) {
        try {
            String SQL_IN_G = (groupids.isEmpty()) ? "" : " idgroup IN " + SQLUtil.SQLINLong(groupids);
            String SQL_IN_U = (usedis.isEmpty()) ? "" : " iduser IN " + SQLUtil.SQLINLong(usedis);
            String SQL_IN_S = (states.isEmpty()) ? "" : " state IN " + SQLUtil.SQLINString(states);
            String W = "";
            if (!SQL_IN_G.isEmpty() || !SQL_IN_U.isEmpty() || !SQL_IN_S.isEmpty()) {
                W += (!SQL_IN_G.isEmpty()) ? SQL_IN_G : "";
                W += (!SQL_IN_U.isEmpty()) ? ((W.isEmpty()) ? SQL_IN_U : " AND " + SQL_IN_U) : "";
                W += (!SQL_IN_S.isEmpty()) ? ((W.isEmpty()) ? SQL_IN_S : " AND " + SQL_IN_S) : "";
                W = " WHERE " + W;
            }
            String SQL = " select * from registration_request " + W;
            SQL += " ORDER BY STR_TO_DATE(dateregistration,'%Y-%m-%d %H:%i:%s') DESC ";
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) -> SQLUtil.getRegistrationRequest(rs));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<RegistrationRequest> getCountByStateAndGroupsIds(List<Long> groupids, String state) {
        try {
            String SQL_IN_G = (groupids.isEmpty()) ? "" : " idgroup IN " + SQLUtil.SQLINLong(groupids);
            String W = "";
            if (!SQL_IN_G.isEmpty()) W = " AND " + SQL_IN_G;

            String SQL = " select idgroup,COUNT(id) n from registration_request WHERE state='" + state + "'  " + W;
            SQL += " GROUP BY idgroup ";
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) ->
                            new RegistrationRequest(
                                    rs.getLong("idgroup"),
                                    rs.getInt("n")
                            )
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
