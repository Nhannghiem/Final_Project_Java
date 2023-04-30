package com.example.demo.service.impl;

import com.example.demo.entity.UsersEntity;
import com.example.demo.reponsitory.UsersReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserSystemDetailImpl implements UserDetailsService {

    @Autowired
    private UsersReponsitory managementReponsitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        UsersEntity sysEmail = managementReponsitory.findByEmail(username);

        if (sysEmail == null){
            throw new UsernameNotFoundException("Not found");
        }

        return new UsersDetails(sysEmail);
    }


}
