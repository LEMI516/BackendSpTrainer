package com.sptrainer.integrationservice.service;

import com.sptrainer.domain.model.Calification;
import com.sptrainer.domain.model.GroupTrainer;
import com.sptrainer.domain.model.Member;
import com.sptrainer.domain.model.RegistrationRequest;
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
public class MemberService {

    @Autowired
    private TokenService tokenService;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseEntity registration_request(RegistrationRequest request) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/member/registration_request";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), request);
            return WebUtil.response(resp, RegistrationRequest.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity response_registration_request(RegistrationRequest request) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/member/response/registration_request";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), request);
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findRequestByGroupIdAndState(Long groupid, String state) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/member/findregistrationrequestbygroupidandstate/" + groupid + "/" + state;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), RegistrationRequest.class);
            List<RegistrationRequest> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (RegistrationRequest) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findMemberByGroupIdAndState(Long groupid, String state) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/member/findmemberbygroupidandstate/" + groupid + "/" + state;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), Member.class);
            List<Member> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (Member) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity update_member(Long idgroup, Long iduser, String state) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/member/update_member/" + idgroup + "/" + iduser + "/" + state;
            ResponseEntity<String> resp = WebUtil.callPutWithOutBody(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup));
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity calificateUpdate(Calification calification) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/calificate/update";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), calification);
            return WebUtil.response(resp, Calification.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findRegistrationByUserIdAndGroupId(Long groupId, Long userId) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/member/findRegistrationByUserIdAndGroupId/" + groupId + "/" + userId;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), RegistrationRequest.class);
            List<RegistrationRequest> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (RegistrationRequest) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

}
