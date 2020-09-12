package com.sptrainer.user.implement;

import com.sptrainer.domain.model.User;
import com.sptrainer.domain.util.SQLUtil;
import com.sptrainer.user.mapper.IUser;
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
import java.util.List;

@Repository
public class UserRepository implements IUser {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "select * from user",
                (rs, rowNum) -> SQLUtil.getUser(rs));
    }

    @Override
    public User save(final User user) {
        final String INSERT_SQL = " INSERT INTO `user`  (document,firstname,lastname,birthdate,phone,password,`user`,rolid,email,weight,height,direction,city,department,profile,sex,photo,coordinate,date_save) " +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CAST(NOW() AS CHAR)) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps = SQLUtil.setDataUser(ps, user);
                        return ps;
                    }
                },
                keyHolder);
        User newUser = user;
        newUser.setId(keyHolder.getKey().longValue());
        return newUser;
    }

    @Override
    public User findByUser(String user) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from user WHERE user=?",
                    new Object[]{user},
                    (rs, rowNum) -> SQLUtil.getUser(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from user WHERE email=?",
                    new Object[]{email},
                    (rs, rowNum) -> SQLUtil.getUser(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByUserorEmail(String data) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from user WHERE email=? OR user=?",
                    new Object[]{data, data},
                    (rs, rowNum) -> SQLUtil.getUser(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int update(User user) {
        String SQL_UPDATE = " UPDATE `user` " +
                " SET `document` = ?,`firstname` = ?," +
                "  `lastname` = ?,`birthdate` = ?," +
                "  `phone` = ?,`weight` = ?,`height` = ?," +
                "  `direction` = ?,`city` = ?,`department` = ?,`profile` = ?,  " +
                "   `sex` = ?,  `photo` = ? , `coordinate` = ? , date_update = CAST(NOW() AS CHAR) " +
                " WHERE `user` = ?";
        return jdbcTemplate.update(SQL_UPDATE,
                user.getDocument(), user.getFirstname(), user.getLastname(), user.getBirthdate(), user.getPhone()
                , user.getWeight(), user.getHeight(), user.getDirection(), user.getCity(), user.getDepartment(), user.getProfile()
                , user.getSex(), user.getProfile(), user.getCoordinate(), user.getUser());
    }

    @Override
    public User findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from  `user` WHERE `id`=?",
                    new Object[]{id},
                    (rs, rowNum) -> SQLUtil.getUser(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int changePassword(Long idUser, String newPassword) {
        String query = " UPDATE `user` " +
                " SET `password` = ? " +
                " WHERE `id` = ? ";
        return jdbcTemplate.update(query,
                newPassword,idUser);
    }
}
