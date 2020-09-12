package com.sptrainer.grouptrainerservice.notification;

import com.sptrainer.domain.model.Notification;
import com.sptrainer.grouptrainerservice.transport.Constans;
import com.sptrainer.grouptrainerservice.transport.Properties;
import com.sptrainer.grouptrainerservice.transport.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NotificationComponent {

    private static final Logger log = LoggerFactory.getLogger(NotificationComponent.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Async
    public void generate(Notification notification) {
        try {
            String url = Properties.URL_ENDPOINT + Constans._ApiGateWay + "/notification/create";
            WebUtil.callPost(webClientBuilder, url, notification);
        } catch (Exception e) {
            log.error("generate->" + e.getMessage());
        }
    }





}
