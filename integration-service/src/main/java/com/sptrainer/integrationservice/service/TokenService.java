package com.sptrainer.integrationservice.service;

import com.sptrainer.domain.model.AutheticationToken;
import com.sptrainer.domain.model.Token;
import com.sptrainer.domain.util.GeneralUtil;
import com.sptrainer.integrationservice.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void refresh(String _app) {
        log.info(" TokenService - refresh ");
        Properties.getToken(_app).setStatus(500);
        try {
            String body = GeneralUtil.mapper.writeValueAsString(new AutheticationToken(Properties.URL_ENDPOINT_USER, Properties.URL_ENDPOINT_PASS));
            String url = Properties.URL_ENDPOINT + Properties.getToken(_app).getApp() + "/auth";
            Mono<ResponseEntity<String>> mono = webClientBuilder.build()
                    .post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Content-Type", "application/json")
                    .body(BodyInserters.fromObject(body))
                    .exchange()
                    .flatMap(resp -> resp.toEntity(String.class));

            ResponseEntity<String> resp = mono.block();
            if (resp.getStatusCodeValue() == 200) {
                Token tkn = GeneralUtil.mapper.readValue(resp.getBody(), Token.class);
                Properties.getToken(_app).setJwttoken(tkn.getJwttoken());
                Properties.getToken(_app).setStatus(200);
                Properties.getToken(_app).setMsj("");
                Properties.getToken(_app).setDate(new Date());
            } else {
                Properties.getToken(_app).setStatus(resp.getStatusCodeValue());
            }
        } catch (Exception e) {
            Properties.getToken(_app).setMsj(e.getMessage());
        }
    }

    public void validateToken(String _app) throws Exception {
        log.info(" TokenService - validarToken " + _app);
        if (Properties.getToken(_app).getStatus() != 200 || GeneralUtil.difMinutes(Properties.getToken(_app).getDate(), new Date()) > 20) {
            refresh(_app);
        }
        if (Properties.getToken(_app).getStatus() != 200) {
            throw new Exception("El token no pudo ser generado, causa " + Properties.getToken(_app).getMsj());
        }
    }
}
