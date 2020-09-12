package com.sptrainer.integrationservice.service;

import com.sptrainer.domain.model.Notification;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private TokenService tokenService;

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseEntity update(List<Notification> notification) {
        try {
            tokenService.validateToken(Constans._AppServiceNotification);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceNotification + "/notification/update";
            ResponseEntity<String> resp = WebUtil.callPut(webClientBuilder, url, Properties.getToken(Constans._AppServiceNotification), notification);
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity createMasive(List<Notification> notification) {
        try {
            tokenService.validateToken(Constans._AppServiceNotification);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceNotification + "/notification/createmasive";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceNotification), notification);
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity create(Notification notification) {
        try {
            tokenService.validateToken(Constans._AppServiceNotification);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceNotification + "/notification/create";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceNotification), notification);
            return WebUtil.response(resp,Notification.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByUser(Long iduser,String rolUser) {
        try {
            tokenService.validateToken(Constans._AppServiceNotification);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceNotification + "/notification/findbyuser/"+iduser+"/"+rolUser;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceNotification), Notification.class);
            List<Notification> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (Notification) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByUserNew(Long iduser,String rolUser) {
        try {
            tokenService.validateToken(Constans._AppServiceNotification);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceNotification + "/notification/findbyusernew/"+iduser+"/"+rolUser;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceNotification), Notification.class);
            List<Notification> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (Notification) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity getNotificationNewsAndRead(Long idUser,String rolUser) {
        try {
            tokenService.validateToken(Constans._AppServiceNotification);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceNotification + "/notification/getNotificationNewsAndRead/"+idUser+"/"+rolUser;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceNotification), Notification.class);
            List<Notification> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (Notification) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity countNotificationNew(Long iduser,String rolUser) {
        try {
            tokenService.validateToken(Constans._AppServiceNotification);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceNotification + "/notification/countnotificationnew/"+iduser+"/"+rolUser;
            ResponseEntity<String> resp = WebUtil.callGet(webClientBuilder, url, Properties.getToken(Constans._AppServiceNotification));
            return new ResponseEntity<>(Integer.parseInt(resp.getBody()), HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

}
