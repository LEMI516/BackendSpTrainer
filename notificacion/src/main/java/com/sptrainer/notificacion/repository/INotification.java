package com.sptrainer.notificacion.repository;

import com.sptrainer.domain.model.Notification;
import com.sptrainer.domain.model.NotificationRead;

import java.util.List;

public interface INotification {

    Notification create(Notification notification);

    int update(Notification notification);

    List<Notification> findByUser(Long iduser,String rolUser);

    int countNotificationNew(Long iduser,String rolUser);

    int read(NotificationRead read);

    List<Notification> findByUserNew(Long iduser,String rolUser);

}
