package com.sptrainer.integrationservice.service;

import com.sptrainer.domain.model.LogLogin;
import com.sptrainer.domain.model.MessageResult;
import com.sptrainer.domain.model.User;
import com.sptrainer.domain.util.GeneralUtil;
import com.sptrainer.integrationservice.util.Constans;
import com.sptrainer.integrationservice.util.Properties;
import com.sptrainer.integrationservice.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private TokenService tokenService;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseEntity findByUserorEmail(String user) {
        try {
            tokenService.validateToken(Constans._AppServiceUser);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceUser + "/user/findbyemailoruser?data=" + user;
            ResponseEntity<String> resp = WebUtil.callGet(webClientBuilder, url, Properties.getToken(Constans._AppServiceUser));
            return WebUtil.response(resp, User.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByUserId(Long id) {
        try {
            tokenService.validateToken(Constans._AppServiceUser);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceUser + "/user/findbyid/"+id;
            ResponseEntity<String> resp = WebUtil.callGet(webClientBuilder, url, Properties.getToken(Constans._AppServiceUser));
            return WebUtil.response(resp, User.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findAll() {
        try {
            tokenService.validateToken(Constans._AppServiceUser);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceUser + "/user/all";
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceUser), User.class);
            List<User> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (User) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity save(User user) {
        try {
            tokenService.validateToken(Constans._AppServiceUser);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceUser + "/user/save";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceUser), user);
            return WebUtil.response(resp, User.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity update(User user) {
        try {
            tokenService.validateToken(Constans._AppServiceUser);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceUser + "/user/update";
            ResponseEntity<String> resp = WebUtil.callPut(webClientBuilder, url, Properties.getToken(Constans._AppServiceUser), user);
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity saveLogLogin(LogLogin logLogin) {
        try {
            tokenService.validateToken(Constans._AppServiceUser);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceUser + "/log/login/save";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceUser), logLogin);
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity changePassword(Long idUser,String newPassword,String oldPassword) {
        try {
            tokenService.validateToken(Constans._AppServiceUser);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceUser + "/user/changePassword?idUser="+idUser+"&newPassword="+newPassword+"&oldPassword="+oldPassword;
            ResponseEntity<String> resp = WebUtil.callPutWithOutBody(webClientBuilder, url, Properties.getToken(Constans._AppServiceUser));
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }


}
