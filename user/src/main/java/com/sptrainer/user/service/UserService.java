package com.sptrainer.user.service;

import com.sptrainer.domain.model.User;
import com.sptrainer.user.mapper.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUser iUser;

    public List<User> findAll() {
        return iUser.findAll();
    }

    public User save(User user) {
        return iUser.save(user);
    }

    public int update(User user) {
        return iUser.update(user);
    }

    public User findByUser(String user) {
        return iUser.findByUser(user);
    }

    public User findByEmail(String email) {
        return iUser.findByEmail(email);
    }

    public User findByUserorEmail(String data) {
        return iUser.findByUserorEmail(data);
    }

    public User findById(Long id) {
        return iUser.findById(id);
    }

    public int changePassword(Long idUser,String oldPassword,String newPassword){
        User user=findById(idUser);
        if(user.getPassword().equals(oldPassword)){
            return iUser.changePassword(idUser,newPassword);
        }else{
            return -5;
        }
    }
}
