package com.sptrainer.integrationservice.util;

import com.sptrainer.domain.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    public static String URL_ENDPOINT;
    public static String URL_ENDPOINT_USER;
    public static String URL_ENDPOINT_PASS;
    public static Token[] tokens = new Token[]{
            new Token("", 0, Constans._AppServiceUser),
            new Token("", 0, Constans._AppServiceGroup),
            new Token("", 0, Constans._AppServiceNotification)
    };


    @Value("${url.endpoing.base}")
    public void setURL_ENDPOINT(String URL_ENDPOINT) {
        this.URL_ENDPOINT = URL_ENDPOINT;
    }

    @Value("${url.endpoing.user}")
    public void setURL_ENDPOINT_USER(String URL_ENDPOINT_USER) {
        this.URL_ENDPOINT_USER = URL_ENDPOINT_USER;
    }

    @Value("${url.endpoing.pass}")
    public void setURL_ENDPOINT_PASS(String URL_ENDPOINT_PASS) {
        this.URL_ENDPOINT_PASS = URL_ENDPOINT_PASS;
    }

    public static Token getToken(String _app) {
        for (Token token : tokens) {
            if (token.getApp().equals(_app)) return token;
        }
        return null;
    }

}
