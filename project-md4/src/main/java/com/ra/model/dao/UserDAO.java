package com.ra.model.dao;

import com.ra.dto.request.UserLoginDTO;
import com.ra.model.entity.User;

import java.util.List;

public interface UserDAO {
    Boolean register(User user);
    User login(UserLoginDTO userLoginDTO);

    Boolean lockuser(Integer id);

    User findById(Integer id);

    List<User> listUser();
    List<String> uniquelist(String string);
}
