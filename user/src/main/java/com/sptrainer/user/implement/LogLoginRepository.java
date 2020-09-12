package com.sptrainer.user.implement;

import com.sptrainer.domain.model.LogLogin;
import com.sptrainer.user.mapper.ILogLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogLoginRepository implements ILogLogin {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(LogLogin logLogin) {
        String query = " INSERT INTO `log_login` " +
                " (`username`,`date_login`,`ip_address`,`result`,`version`) " +
                " VALUES " +
                " (?,CAST(NOW() AS CHAR),?,?,?) ";
        return jdbcTemplate.update(query,
                logLogin.getUsername(), logLogin.getIpAddress(), logLogin.getResult(),logLogin.getVersion());
    }
}
