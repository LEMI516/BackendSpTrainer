package com.sptrainer.notificacion.controller;

import com.sptrainer.domain.model.Notification;
import com.sptrainer.domain.model.RegistrationRequest;
import com.sptrainer.notificacion.service.NotificationService;
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
        return new ResponseEntity<>(notificationService.create(notification), HttpStatus.OK);
    }

    @PostMapping("/createmasive")
    public @ResponseBody
    ResponseEntity createmasive(@RequestBody List<Notification> notification) {
        return new ResponseEntity<>(notificationService.createMasive(notification), HttpStatus.OK);
    }

    @PutMapping("/update")
    public @ResponseBody
    ResponseEntity update(@RequestBody List<Notification> notification) {
        return new ResponseEntity<>(notificationService.update(notification), HttpStatus.OK);
    }

    @GetMapping("/findbyuser/{iduser}/{rolUser}")
    public @ResponseBody
    ResponseEntity findByUser(@PathVariable("iduser") Long iduser,@PathVariable("rolUser") String rolUser) {
        return new ResponseEntity<>(notificationService.findByUser(iduser,rolUser), HttpStatus.OK);
    }

    @GetMapping("/findbyusernew/{iduser}/{rolUser}")
    public @ResponseBody
    ResponseEntity findByUserNew(@PathVariable("iduser") Long iduser,@PathVariable("rolUser") String rolUser) {
        return new ResponseEntity<>(notificationService.findByUserNew(iduser,rolUser), HttpStatus.OK);
    }

    @GetMapping("/countnotificationnew/{iduser}/{rolUser}")
    public @ResponseBody
    ResponseEntity countNotificationNew(@PathVariable("iduser") Long iduser,@PathVariable("rolUser") String rolUser) {
        return new ResponseEntity<>(notificationService.countNotificationNew(iduser,rolUser), HttpStatus.OK);
    }

    @GetMapping("/getNotificationNewsAndRead/{idUser}/{rolUser}")
    public @ResponseBody
    ResponseEntity getNotificationNewsAndRead(@PathVariable("idUser") Long idUser,@PathVariable("rolUser") String rolUser) {
        return new ResponseEntity<>(notificationService.getNotificationNewsAndRead(idUser,rolUser), HttpStatus.OK);
    }

}
