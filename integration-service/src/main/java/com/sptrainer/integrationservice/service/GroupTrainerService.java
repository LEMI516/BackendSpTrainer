package com.sptrainer.integrationservice.service;

import com.sptrainer.domain.model.*;
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
public class GroupTrainerService {

    @Autowired
    private TokenService tokenService;

    private static final Logger log = LoggerFactory.getLogger(GroupTrainerService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseEntity findByUserId(Long userId) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/findbyuserid/" + userId;
            ResponseEntity<String> resp = WebUtil.callGet(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup));
            return WebUtil.responseList(resp, GroupTrainer.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByUserIdForClient(Long userId) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/findbyuseridforclient/" + userId;
            ResponseEntity<String> resp = WebUtil.callGet(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup));
            return WebUtil.responseList(resp, GroupTrainer.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByTrainer(Long userId) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/findByTrainer/" + userId;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup),GroupTrainer.class);
            List<GroupTrainer> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (GroupTrainer) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByGroupIdAndUserId(Long userId, Long groupId) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/findbygroupidanduserid/" + userId + "/" + groupId;
            ResponseEntity<String> resp = WebUtil.callGet(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup));
            return WebUtil.response(resp, GroupTrainer.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity save(GroupTrainer groupTrainer) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/save";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), groupTrainer);
            return WebUtil.response(resp, GroupTrainer.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity update(GroupTrainer groupTrainer) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/update";
            ResponseEntity<String> resp = WebUtil.callPut(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), groupTrainer);
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity publish(Long iduser, Long idgroup) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/publish/" + idgroup + "/" + iduser;
            ResponseEntity<String> resp = WebUtil.callPutWithOutBody(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup));
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByMultipleFilter(String idusers, String categories, String filter, String coordinate, Long iduser) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/findbymultiplefilter";
            String paramRequest = "?coordinate=" + coordinate + "&iduser=" + iduser;
            if (filter != null && !filter.isEmpty()) paramRequest += "&filter=" + filter;
            if (categories != null && !categories.isEmpty()) paramRequest += "&categories=" + categories;
            if (idusers != null && !idusers.isEmpty()) paramRequest += "&idusers=" + idusers;
            url += paramRequest;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), GroupTrainer.class);
            List<GroupTrainer> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (GroupTrainer) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findGroupsInit(String coordinate, Long iduser) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/findbygroupinits?coordinate=" + coordinate + "&iduser=" + iduser;
            ResponseGroupInitDTO resp = (ResponseGroupInitDTO) WebUtil.callGet(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), ResponseGroupInitDTO.class);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByMember(Long iduser) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/findbymember/"+iduser;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), GroupTrainer.class);
            List<GroupTrainer> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (GroupTrainer) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findByIdCompleteInfo(Long idgroup,Long iduser,String coordinate) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/group/findbyidcompleteinfo/"+idgroup+"/"+iduser+"?coordinate="+coordinate;
            GroupTrainer g = (GroupTrainer) WebUtil.callGet(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), GroupTrainer.class);
            return new ResponseEntity<>(g, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }


    /*Sesion Trainer*/
    public ResponseEntity saveSession(SesionTrainer session) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/sesion/save";
            ResponseEntity<String> resp = WebUtil.callPost(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), session);
            return WebUtil.response(resp, SesionTrainer.class);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity updateSession(SesionTrainer session) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/sesion/update";
            ResponseEntity<String> resp = WebUtil.callPut(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), session);
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity deleteSession(Long idsesion, Long idgroup, Long iduser) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/sesion/delete/" + idsesion + "/" + idgroup + "/" + iduser;
            ResponseEntity<String> resp = WebUtil.callDelete(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup));
            return WebUtil.responseIntValue(resp);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }

    public ResponseEntity findSessionByUserAndGroupId(Long idgroup, Long iduser) {
        try {
            tokenService.validateToken(Constans._AppServiceGroup);
            String url = Properties.URL_ENDPOINT + Constans._AppServiceGroup + "/sesion/findbygroupid/" + idgroup + "/" + iduser;
            List<Object> resp = WebUtil.callGetList(webClientBuilder, url, Properties.getToken(Constans._AppServiceGroup), SesionTrainer.class);
            List<SesionTrainer> list = new ArrayList<>();
            if (!resp.isEmpty()) list = resp.stream().map(x -> (SesionTrainer) x).collect(Collectors.toList());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return WebUtil.responseError(e.getMessage());
        }
    }


}
