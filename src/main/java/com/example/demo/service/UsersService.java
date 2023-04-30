package com.example.demo.service;

import com.example.demo.entity.UsersEntity;
import com.example.demo.form.UsersForm;
import com.example.demo.model.CountReponse;

public interface UsersService {

    UsersEntity saveUser(UsersForm systemManagementForm);


    UsersEntity saveUserRes(UsersForm systemManagementForm);

    UsersEntity findByEmail(String email);

    Long checkUser();

    CountReponse countUsers();

}
