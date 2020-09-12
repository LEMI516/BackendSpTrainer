package com.sptrainer.grouptrainerservice.transport;

import com.sptrainer.domain.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {
    public static String URL_ENDPOINT;

    @Value("${url.endpoing.base}")
    public void setURL_ENDPOINT(String URL_ENDPOINT) {
        this.URL_ENDPOINT = URL_ENDPOINT;
    }
}
