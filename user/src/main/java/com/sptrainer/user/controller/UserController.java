package com.sptrainer.user.controller;

import com.sptrainer.domain.model.PasswordEncodeUtil;
import com.sptrainer.domain.model.User;
import com.sptrainer.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public @ResponseBody
    List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/findbyuser")
    public @ResponseBody
    ResponseEntity findByUser(@RequestParam("user") String user) {
        User usr = userService.findByUser(user);
        if (usr != null && usr.getUser() != null && !usr.getUser().isEmpty()) {
            return new ResponseEntity<>(usr, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @GetMapping("/findbyemail")
    public @ResponseBody
    ResponseEntity findByEmail(@RequestParam("email") String email) {
        User usr = userService.findByEmail(email);
        if (usr != null && usr.getUser() != null && !usr.getUser().isEmpty()) {
            return new ResponseEntity<>(usr, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email no encontrado");
        }
    }

    @PostMapping("/save")
    private @ResponseBody
    ResponseEntity save(@RequestBody User user) {
        User usefound = userService.findByUser(user.getUser());
        if (usefound != null && usefound.getUser() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario registrado con " + user.getUser());
        } else {
            User userfoundemail = userService.findByEmail(user.getEmail());
            if (userfoundemail != null && userfoundemail.getUser() != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con el mismo e-mail registrado");
            } else {
                User newUser = userService.save(user);
                return new ResponseEntity<>(newUser, HttpStatus.OK);
            }
        }
    }

    @PutMapping("/update")
    private @ResponseBody
    int update(@RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/get_password")
    public @ResponseBody
    String get_password(@RequestParam("key") String key) {
        String msj = "";
        try {
            msj = PasswordEncodeUtil.getEncodePassword(key);
        } catch (NoSuchAlgorithmException e) {
            msj = e.getMessage();
        }
        return msj;
    }

    @GetMapping("/findbyemailoruser")
    public @ResponseBody
    ResponseEntity findByUserorEmail(@RequestParam("data") String data) {
        User usr = userService.findByUserorEmail(data);
        if (usr != null && usr.getUser() != null && !usr.getUser().isEmpty()) {
            return new ResponseEntity<>(usr, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @GetMapping("/findbyid/{id}")
    public @ResponseBody
    ResponseEntity findById(@PathVariable("id") Long id) {
        User usr = userService.findById(id);
        if (usr != null && usr.getUser() != null && !usr.getUser().isEmpty()) {
            return new ResponseEntity<>(usr, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PutMapping("/changePassword")
    private @ResponseBody
    ResponseEntity changePassword(@RequestParam("idUser") Long idUser,
                       @RequestParam("newPassword") String newPassword,
                       @RequestParam("oldPassword") String oldPassword) {
        int result=userService.changePassword(idUser,oldPassword,newPassword);
        if(result==-5){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Contraseña antigua incorrecta");
        } else
            return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
