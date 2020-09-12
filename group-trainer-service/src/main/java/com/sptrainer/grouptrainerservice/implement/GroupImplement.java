package com.sptrainer.grouptrainerservice.implement;

import com.sptrainer.domain.model.GroupTrainer;
import com.sptrainer.domain.model.SearchGroupRequestDTO;
import com.sptrainer.domain.util.SQLUtil;
import com.sptrainer.grouptrainerservice.repository.IGroup;
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
public class GroupImplement implements IGroup {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GroupTrainer> findByUserId(Long userId) {
        try {
            return jdbcTemplate.query(
                    "select * from grouptrainer WHERE iduser=? ",
                    new Object[]{userId},
                    (rs, rowNum) -> SQLUtil.getGroupTrainer(rs));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public GroupTrainer findByGroupIdAndUserId(Long userId, Long groupId) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from grouptrainer WHERE iduser=? AND id=?",
                    new Object[]{userId, groupId},
                    (rs, rowNum) ->
                            SQLUtil.getGroupTrainer(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public GroupTrainer save(GroupTrainer groupTrainer) {
        final String INSERT_SQL = " INSERT INTO `grouptrainer`  " +
                " (`iduser`,`name`,`description`,`quantity`,`active`,`state`,`enddate`,`startdate`,`type_schedule`,`address`,`sitie`,`coordinate`,`colour`,`category`,date_save)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,CAST(NOW() AS CHAR)) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                ps = SQLUtil.setDataGroupTrainer(ps, groupTrainer);
                return ps;
            }
        }, keyHolder);
        groupTrainer.setId(keyHolder.getKey().longValue());
        return groupTrainer;
    }

    @Override
    public int update(GroupTrainer groupTrainer) {
        final String UPDATE_SQL = "update `grouptrainer` " +
                "set `name` = ?,`description` = ?, `quantity` = ?, " +
                "  `active` = ?,`state` = ?,`enddate` = ?,`startdate` = ?, " +
                "  `type_schedule` = ?,`address` = ?,`sitie` = ?,`coordinate` = ?, " +
                "  `colour` = ?, `category`= ? , date_update = CAST(NOW() AS CHAR) " +
                "where `id` = ? AND  `iduser` = ?;";
        return jdbcTemplate.update(UPDATE_SQL,
                groupTrainer.getName(), groupTrainer.getDescription(), groupTrainer.getQuantity(), groupTrainer.getActive(), groupTrainer.getState()
                , groupTrainer.getEnddate(), groupTrainer.getStartdate(), groupTrainer.getType_schedule(), groupTrainer.getAddress(), groupTrainer.getSitie(), groupTrainer.getCoordinate()
                , groupTrainer.getColour(), groupTrainer.getCategory(), groupTrainer.getId(), groupTrainer.getIduser());
    }

    @Override
    public List<GroupTrainer> findByMultipleFilter(SearchGroupRequestDTO requestDTO) {
        try {
            String SQL = " SELECT t1.* FROM " +
                    "`grouptrainer` t1 " +
                    "INNER JOIN `sesion` t2 ON t2.`idgroup`=t1.`id` AND t2.`active`='true' " +
                    "WHERE t1.`active`='true' AND t1.`state`='PUB' AND " +
                    "STR_TO_DATE(t1.`enddate`,'%Y-%m-%d')>=STR_TO_DATE(DATE_FORMAT(NOW(),'%Y%-%m-%d'),'%Y-%m-%d') AND " +
                    "STR_TO_DATE(t1.`startdate`,'%Y-%m-%d')<=STR_TO_DATE(DATE_FORMAT(NOW(),'%Y%-%m-%d'),'%Y-%m-%d') ";
            if (requestDTO.getIduser() != null && !requestDTO.getIduser().isEmpty())
                SQL += " AND t1.iduser IN " + SQLUtil.SQLINLong(requestDTO.getIduser()) + " ";
            if (requestDTO.getCategory() != null && !requestDTO.getCategory().isEmpty())
                SQL += " AND t1.category IN " + SQLUtil.SQLINString(requestDTO.getCategory()) + " ";
            if (requestDTO.getFilter() != null && !requestDTO.getFilter().isEmpty()) {
                SQL += "AND  (t1.`name` LIKE '%" + requestDTO.getFilter() + "%' OR " +
                        " t1.`description` LIKE '%" + requestDTO.getFilter() + "%' OR " +
                        " t2.`description` LIKE '%" + requestDTO.getFilter() + "%') ";
            }
            SQL += " GROUP BY t1.`id` ";
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) -> SQLUtil.getGroupTrainer(rs));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int publish(Long userId, Long groupId) {
        final String UPDATE_SQL = "update `grouptrainer` " +
                "set `state` = 'PUB' , date_publish = CAST(NOW() AS CHAR) " +
                "where `id` = ? AND  `iduser` = ?;";
        return jdbcTemplate.update(UPDATE_SQL,
                groupId, userId);
    }

    @Override
    public List<GroupTrainer> findByMember(Long iduser) {
        try {
            String SQL = " SELECT * FROM `grouptrainer` t1 " +
                    " INNER JOIN `member` t2 ON t2.`idgroup`=t1.`id` AND t2.`iduser`="+iduser+" AND t2.`state`='ACTIVE' " +
                    " GROUP BY t1.`id`";
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) -> SQLUtil.getGroupTrainer(rs));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public GroupTrainer findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from grouptrainer WHERE id=?",
                    new Object[]{id},
                    (rs, rowNum) ->
                            SQLUtil.getGroupTrainer(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int getQuotasAvailable(Long id) {
        try {
            String query=" SELECT(g.`quantity`-COUNT(m.`iduser`)) quotas_avaiable " +
                    " FROM `grouptrainer` g\n" +
                    " LEFT JOIN `member` m ON m.`idgroup`=g.`id` AND m.`state`='ACTIVE' " +
                    " WHERE g.`id`="+id+" " +
                    " GROUP BY g.`id`";
            return jdbcTemplate.queryForObject(query,Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }
}
