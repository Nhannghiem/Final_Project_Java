package com.example.demo.service.impl;

import com.example.demo.entity.UsersEntity;
import com.example.demo.form.UsersForm;
import com.example.demo.model.CountData;
import com.example.demo.model.CountReponse;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsersImpl implements UsersService {


    @Autowired
    private UsersReponsitory managementReponsitory;

    @Override
    public UsersEntity saveUser(UsersForm usersForm) {

        UsersEntity sy = new UsersEntity();
        sy.setPassword(usersForm.getPassword());
        sy.setRole(usersForm.getRole());
        sy.setPhoto(usersForm.getPhoto());
        sy.setEmail(usersForm.getEmail());
        sy.setAccountName(usersForm.getAccountName());

        return managementReponsitory.save(sy);
    }

    @Override
    public UsersEntity saveUserRes(UsersForm systemManagementForm) {
        UsersEntity sy = new UsersEntity();
        sy.setPassword(systemManagementForm.getPassword());
        sy.setRole("user");
        sy.setEmail(systemManagementForm.getEmail());
        sy.setPhoto(systemManagementForm.getPhoto());
        sy.setAccountName(systemManagementForm.getAccountName());

        return managementReponsitory.save(sy);
    }

    @Override
    public UsersEntity findByEmail(String email) {
        return managementReponsitory.findByEmail(email);
    }

    @Override
    public Long checkUser() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return managementReponsitory.findByEmail(userEmail).getId();
    }

    @Override
    public CountReponse countUsers() {
        CountData countData = managementReponsitory.countUsers();
        if (countData != null){
            return CountReponse.builder().countUser(countData.getCountUser()).build();
        }else {
            return CountReponse.builder().countUser(0).build();
        }
    }
}
