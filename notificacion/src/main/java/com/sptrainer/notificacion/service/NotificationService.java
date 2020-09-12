package com.sptrainer.notificacion.service;

import com.sptrainer.domain.model.Notification;
import com.sptrainer.domain.model.NotificationRead;
import com.sptrainer.notificacion.repository.INotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private INotification iNotification;

    public Notification create(Notification notification){
        return iNotification.create(notification);
    }

    public int update(List<Notification> notification){
        notification.forEach(x->{
            NotificationRead read=new NotificationRead();
            read.setIdnotification(x.getId());
            read.setState("READ");
            read.setIduser(x.getIduserread());
            iNotification.read(read);
        });
        return 1;
    }

    public int createMasive(List<Notification> notifications){
        notifications.forEach(x->{
            iNotification.create(x);
        });
        return 1;
    }

    public List<Notification> findByUser(Long iduser,String rolUser){
        return iNotification.findByUser(iduser,rolUser);
    }

    public int countNotificationNew(Long iduser,String rolUser){
        return iNotification.countNotificationNew(iduser,rolUser);
    }

    public List<Notification> findByUserNew(Long iduser,String rolUser){
        return iNotification.findByUserNew(iduser,rolUser);
    }

    public List<Notification> getNotificationNewsAndRead(Long idUser,String rolUser){
        List<Notification> notificationList=iNotification.findByUserNew(idUser,rolUser);
        if(!notificationList.isEmpty()){
            for(Notification notification:notificationList) notification.setIduserread(idUser);
            update(notificationList);
        }
        return notificationList;
    }


}
