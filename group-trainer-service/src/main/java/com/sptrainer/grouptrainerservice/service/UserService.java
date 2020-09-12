package com.sptrainer.grouptrainerservice.service;

import com.sptrainer.domain.model.SesionTrainer;
import com.sptrainer.domain.model.User;
import com.sptrainer.domain.util.GeneralUtil;
import com.sptrainer.grouptrainerservice.transport.Constans;
import com.sptrainer.grouptrainerservice.transport.Properties;
import com.sptrainer.grouptrainerservice.transport.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<User> findAllUser() {
        try {
            String url = Properties.URL_ENDPOINT + Constans._ApiGateWay + "/user/all";
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, User.class);
            List<User> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (User) x).collect(Collectors.toList());
            list = list.parallelStream().map(x -> GeneralUtil.getUser(x)).collect(Collectors.toList());
            return list;
        } catch (Exception e) {
            log.error("findAllUser->" + e.getMessage());
            return new ArrayList<>();
        }
    }

    public User findByUserId(Long iduser) {
        try {
            String url = Properties.URL_ENDPOINT + Constans._ApiGateWay + "/user/findbyid/"+iduser;
            ResponseEntity<String> resp = WebUtil.callGet(webClientBuilder, url);
            return GeneralUtil.mapper.readValue(resp.getBody(), User.class);
        } catch (Exception e) {
            log.error("findByUserId->" + e.getMessage());
            return null;
        }
    }


}
