package com.sptrainer.user.mapper;

import com.sptrainer.domain.model.User;


import java.util.List;

public interface IUser {

    List<User> findAll();

    User save(User user);

    User findByUser(String user);

    User findByEmail(String email);

    User findByUserorEmail(String data);

    int update(User user);

    User findById(Long id);

    int changePassword(Long idUser,String newPassword);

}
