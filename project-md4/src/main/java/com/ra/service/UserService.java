package com.ra.service;

import com.ra.dto.request.UserLoginDTO;
import com.ra.dto.request.UserRegisterDTO;
import com.ra.dto.response.ResponseUserLoginDTO;
import com.ra.model.entity.User;

import java.util.List;

public interface UserService {
    Boolean register(UserRegisterDTO user);
    ResponseUserLoginDTO login(UserLoginDTO user);

    ResponseUserLoginDTO loginAdmin(UserLoginDTO user);
    Boolean lockuser(Integer id);
    List<ResponseUserLoginDTO> listUser();
    List<String> uniquelist(String string);
}
