package com.sptrainer.user.service;

import com.sptrainer.domain.model.LogLogin;
import com.sptrainer.user.mapper.ILogLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogLoginService {

    @Autowired
    private ILogLogin iLogLogin;

    public int save(LogLogin logLogin){
        return iLogLogin.save(logLogin);
    }

}
