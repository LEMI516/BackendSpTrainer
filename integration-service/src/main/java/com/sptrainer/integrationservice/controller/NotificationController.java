package com.sptrainer.integrationservice.controller;

import com.sptrainer.domain.model.Notification;
import com.sptrainer.integrationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public @ResponseBody
    ResponseEntity create(@RequestBody Notification notification) {
        return notificationService.create(notification);
    }

    @PutMapping("/update")
    public @ResponseBody
    ResponseEntity update(@RequestBody List<Notification> notification) {
        return notificationService.update(notification);
    }

    @PostMapping("/createmasive")
    public @ResponseBody
    ResponseEntity createmasive(@RequestBody List<Notification> notification) {
        return notificationService.createMasive(notification);
    }

    @GetMapping("/findbyuser/{iduser}/{rolUser}")
    public @ResponseBody
    ResponseEntity findByUser(@PathVariable("iduser") Long iduser,@PathVariable("rolUser") String rolUser) {
        return notificationService.findByUser(iduser,rolUser);
    }

    @GetMapping("/findbyusernew/{iduser}/{rolUser}")
    public @ResponseBody
    ResponseEntity findByUserNew(@PathVariable("iduser") Long iduser,@PathVariable("rolUser") String rolUser) {
        return notificationService.findByUserNew(iduser,rolUser);
    }

    @GetMapping("/countnotificationnew/{iduser}/{rolUser}")
    public @ResponseBody
    ResponseEntity countNotificationNew(@PathVariable("iduser") Long iduser,@PathVariable("rolUser") String rolUser) {
        return notificationService.countNotificationNew(iduser,rolUser);
    }

    @GetMapping("/getNotificationNewsAndRead/{idUser}/{rolUser}")
    public @ResponseBody
    ResponseEntity getNotificationNewsAndRead(@PathVariable("idUser") Long idUser,@PathVariable("rolUser") String rolUser) {
        return notificationService.getNotificationNewsAndRead(idUser,rolUser);
    }

}
