package com.sptrainer.integrationservice.controller;

import com.sptrainer.domain.model.LogLogin;
import com.sptrainer.domain.model.MessageResult;
import com.sptrainer.domain.model.User;
import com.sptrainer.integrationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public @ResponseBody
    ResponseEntity findAll() {
        return userService.findAll();
    }

    @GetMapping("/findbyuser")
    public @ResponseBody
    ResponseEntity findByUserorEmail(@RequestParam("user") String user) {
        return userService.findByUserorEmail(user);
    }

    @GetMapping("/findbyid/{id}")
    public @ResponseBody
    ResponseEntity findById(@PathVariable("id") Long id) {
        return userService.findByUserId(id);
    }

    @PostMapping("/save")
    private @ResponseBody
    ResponseEntity save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/update")
    private @ResponseBody
    ResponseEntity update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/log/login/save")
    private @ResponseBody
    ResponseEntity logLoginSave(@RequestBody LogLogin logLogin) {
        return userService.saveLogLogin(logLogin);
    }

    @PutMapping("/changePassword")
    private @ResponseBody
    ResponseEntity changePassword(@RequestParam("idUser") Long idUser,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("oldPassword") String oldPassword) {
        return userService.changePassword(idUser,newPassword,oldPassword);
    }


}
