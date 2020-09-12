package com.sptrainer.grouptrainerservice.implement;

import com.sptrainer.domain.model.SesionTrainer;
import com.sptrainer.domain.util.SQLUtil;
import com.sptrainer.grouptrainerservice.repository.ISesion;
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
public class SesionImplement implements ISesion {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SesionTrainer save(SesionTrainer sesion) {
        final String INSERT_SQL = " insert into `sesion` " +
                " (`idgroup`,`iduser`,`startday`,`endday`,`starthour`,`endhour`,`active`,`address`,`description`,`coordinate`,`name`,`sitiedefault`,date_save) " +
                " values " +
                " (?,?,?,?,?,?,?,?,?,?,?,?,CAST(NOW() AS CHAR)) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                ps = SQLUtil.setDataSesionTrainer(ps, sesion);
                return ps;
            }
        }, keyHolder);
        sesion.setId(keyHolder.getKey().longValue());
        return sesion;
    }

    @Override
    public int update(SesionTrainer sesion) {
        final String UPDATE_SQL = " UPDATE `sesion` " +
                " SET `startday` = ?,`endday` = ?,`starthour` = ?, " +
                "  `endhour` = ?,`active` = ?,`address` = ?,`description` = ?, " +
                "  `coordinate` = ?,`name` = ?,`sitiedefault` =? , date_update = CAST(NOW() AS CHAR) " +
                " WHERE `id` =  ? AND `idgroup` = ? AND `iduser`=  ? ;";
        return jdbcTemplate.update(UPDATE_SQL,
                sesion.getStartday(), sesion.getEndday(), sesion.getStarthour(), sesion.getEndhour(), sesion.getActive()
                , sesion.getAddress(), sesion.getDescription(), sesion.getCoordinate(), sesion.getName(), sesion.getSitiedefault()
                , sesion.getId(), sesion.getIdgroup(), sesion.getIduser());
    }

    @Override
    public int delete(Long idsesion, Long idgroup, Long iduser) {
        final String DELETE_SQL = " DELETE FROM `sesion` " +
                " WHERE `id` =  ? AND `idgroup` = ? AND `iduser`=  ? ;";
        return jdbcTemplate.update(DELETE_SQL, idsesion, idgroup, iduser);
    }

    @Override
    public List<SesionTrainer> findByGroupId(Long groupid) {
        try {
            return jdbcTemplate.query(
                    "select * from sesion WHERE idgroup=? ",
                    new Object[]{groupid},
                    (rs, rowNum) -> SQLUtil.getSesionTrainer(rs));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<SesionTrainer> findByGroupIdS(List<Long> groupids) {
        try {
            String SQL = " select * from sesion WHERE idgroup IN " + SQLUtil.SQLINLong(groupids);
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) -> SQLUtil.getSesionTrainer(rs));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
