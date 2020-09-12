package com.sptrainer.grouptrainerservice.notification;

import com.sptrainer.domain.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class NotificationAsync {

    @Autowired
    private NotificationComponent notificationComponent;

    public void generate(Notification notification){
        notificationComponent.generate(notification);
    }
}
