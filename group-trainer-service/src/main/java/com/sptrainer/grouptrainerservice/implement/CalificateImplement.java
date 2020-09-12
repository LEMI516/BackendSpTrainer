package com.sptrainer.grouptrainerservice.implement;

import com.sptrainer.domain.model.Calification;
import com.sptrainer.domain.util.SQLUtil;
import com.sptrainer.grouptrainerservice.repository.ICalificate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CalificateImplement implements ICalificate {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Calification save(Calification calification) {
        final String INSERT_SQL = " INSERT INTO `calification`  " +
                " (`idgroup`,`iduser`,`idsesion`,`score`,`idusercalificate`,`datecalificate`) " +
                " VALUES (?,?,?,?,?,CAST(NOW() AS CHAR)) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                ps = SQLUtil.setDataCalificate(ps, calification);
                return ps;
            }
        }, keyHolder);
        calification.setId(keyHolder.getKey().longValue());
        return calification;
    }

    @Override
    public int update(Calification calification) {
        final String UPDATE_SQL = " UPDATE `calification` SET " +
                " score=? " +
                " WHERE id=? ";
         return jdbcTemplate.update(UPDATE_SQL,
                calification.getScore(),
                calification.getId());
    }

    @Override
    public List<Calification> findByGroupIdsOrUserIds(List<Long> iduser, List<Long> idgroup,List<Long> idusercalificate) {
        try {
            String sqlInGroup = (idgroup.isEmpty()) ? "" : " idgroup IN " + SQLUtil.SQLINLong(idgroup);
            String sqlInUser = (iduser.isEmpty()) ? "" : " iduser IN " + SQLUtil.SQLINLong(iduser);
            String sqlInUserCalificate = (idusercalificate.isEmpty()) ? "" : " idusercalificate IN " + SQLUtil.SQLINLong(idusercalificate);

            String where = "";
            if (!sqlInGroup.isEmpty() || !sqlInUser.isEmpty() || !sqlInUserCalificate.isEmpty()) {
                where += (!sqlInGroup.isEmpty()) ? sqlInGroup : "";
                where += SQLUtil.validateEmpity(sqlInUser,where);
                where += SQLUtil.validateEmpity(sqlInUserCalificate,where);
                where = " WHERE " + where;
            }
            String sql = " select * from calification " + where;
            return jdbcTemplate.query(
                    sql,
                    new Object[]{},
                    (rs, rowNum) -> SQLUtil.getCalificate(rs));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Calification> getAVGforUserIds(List<Long> iduser) {
        try {
            String sqlInUser = (iduser.isEmpty()) ? "" : " iduser IN " + SQLUtil.SQLINLong(iduser);
            String where = "";
            if (!sqlInUser.isEmpty()) {
                where= "  WHERE "+sqlInUser;
            }
            String sql = " select iduser,IFNULL(ROUND(AVG(score),1),0) score from calification " + where +
                    " GROUP BY iduser ";
            return jdbcTemplate.query(
                    sql,
                    new Object[]{},
                    (rs, rowNum) -> new Calification(rs.getLong("iduser"),rs.getDouble("score"))
                    );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Calification> getQualificationByUserIdsAndGroupIdsAndGeneral(List<Long> idsUser, List<Long> idsGroup) {
        try {
            String sqlInGroup = (idsGroup.isEmpty()) ? "" : "  IN " + SQLUtil.SQLINLong(idsGroup);
            String sqlInUser = (idsUser.isEmpty()) ? "" : "  IN " + SQLUtil.SQLINLong(idsUser);

            String sql = " SELECT t1.*,t2.score_total FROM calification t1 " +
                    " INNER JOIN " +
                    " (SELECT iduser,IFNULL(ROUND(AVG(score),1),0) score_total " +
                    " FROM calification WHERE iduser "+sqlInUser+" " +
                    " GROUP BY iduser) t2 ON t2.iduser=t1.iduser " +
                    " WHERE t1.iduser  "+sqlInUser+" AND t1.`idgroup` "+sqlInGroup;
            return jdbcTemplate.query(
                    sql,
                    new Object[]{},
                    (rs, rowNum) -> new Calification(rs.getLong("id"),rs.getLong("iduser"),rs.getLong("idgroup"),rs.getLong("score"),rs.getLong("idusercalificate"),rs.getDouble("score_total"))
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
