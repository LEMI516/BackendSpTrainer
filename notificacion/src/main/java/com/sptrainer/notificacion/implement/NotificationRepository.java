package com.sptrainer.notificacion.implement;

import com.sptrainer.domain.model.Notification;
import com.sptrainer.domain.model.NotificationRead;
import com.sptrainer.domain.util.SQLUtil;
import com.sptrainer.notificacion.repository.INotification;
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
public class NotificationRepository implements INotification {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Notification create(Notification notification) {
        final String INSERT_SQL = " INSERT INTO `notification`  " +
                " (`idgroup`,`iduser`,`colour`,`description`,`type_notification`,`id_registration_request`,`idsesion`,`datenotificacion`,`state`,idusergenerate)" +
                " VALUES (?,?,?,?,?,?,?,CAST(NOW() AS CHAR),?,?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                ps = SQLUtil.setDataNotification(ps, notification);
                return ps;
            }
        }, keyHolder);
        notification.setId(keyHolder.getKey().longValue());
        return notification;
    }

    @Override
    public int update(Notification notification) {
        final String UPDATE_SQL = "update `notification` " +
                "set `state` = ? " +
                "where `id` = ? ";
        return jdbcTemplate.update(UPDATE_SQL
                ,notification.getState()
                ,notification.getId());
    }

    @Override
    public List<Notification> findByUser(Long iduser,String rolUser) {
        try {
            String SQL = " SELECT t1.*,IF(IFNULL(t2.`idnotification`,'SI')='SI','NO','SI') state_read " +
                    " FROM `notification` t1 " +
                    " LEFT JOIN `notification_read` t2 ON t2.`idnotification`=t1.`id` AND t2.`iduser`="+iduser+" " +
                    " LEFT JOIN `member` t3 ON t3.`iduser`="+iduser+" AND t3.`idgroup`=t1.`idgroup` " +
                    " WHERE (t1.iduser="+iduser+" OR (t1.type_notification='GENERAL' AND 'client'='"+rolUser+"' ) OR t3.`idgroup` IS NOT NULL ) " +
                    " GROUP BY t1.id " +
                    " ORDER BY STR_TO_DATE(datenotificacion,'%Y-%m-%d %H:%i:%s') DESC ";
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) -> SQLUtil.getNotification(rs));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int countNotificationNew(Long iduser,String rolUser) {
        try {
            String sql = " SELECT COUNT(*) FROM `notification` t1 " +
                    " LEFT JOIN `notification_read` t2 ON t2.`idnotification`=t1.`id` AND t2.`iduser`="+iduser+" " +
                    " WHERE(t1.iduser="+iduser+" OR (t1.type_notification='GENERAL' AND 'client'='"+rolUser+"' ) OR t3.`idgroup` IS NOT NULL) AND " +
                    " IF(IFNULL(t2.`idnotification`,'SI')='SI','NO','SI')='NO' ";
            return jdbcTemplate.queryForObject(
                    sql, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int read(NotificationRead read) {
        final String INSERT_SQL = " INSERT INTO `notification_read`  " +
                " (`idnotification`,`state`,`dateread`,`iduser`)" +
                " VALUES (?,?,CAST(NOW() AS CHAR),?) ";
        return jdbcTemplate.update(INSERT_SQL,
                read.getIdnotification(), read.getState(),read.getIduser());
    }

    @Override
    public List<Notification> findByUserNew(Long iduser,String rolUser) {
        try {
            String SQL = " SELECT t1.*,IF(IFNULL(t2.`idnotification`,'SI')='SI','NO','SI') state_read " +
                    " FROM `notification` t1 " +
                    " LEFT JOIN `notification_read` t2 ON t2.`idnotification`=t1.`id` AND t2.`iduser`="+iduser+" " +
                    " LEFT JOIN `member` t3 ON t3.`iduser`="+iduser+" AND t3.`idgroup`=t1.`idgroup` " +
                    " WHERE (t1.iduser="+iduser+" OR (t1.type_notification='GENERAL' AND 'client'='"+rolUser+"' ) OR t3.`idgroup` IS NOT NULL)  AND " +
                    " IF(IFNULL(t2.`idnotification`,'SI')='SI','NO','SI')='NO' " +
                    " GROUP BY t1.id " +
                    " ORDER BY STR_TO_DATE(datenotificacion,'%Y-%m-%d %H:%i:%s') DESC ";
            return jdbcTemplate.query(
                    SQL,
                    new Object[]{},
                    (rs, rowNum) -> SQLUtil.getNotification(rs));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
