package com.example.demo.service;

import com.example.demo.entity.ViewEntity;

public interface ViewService {


    void saveViewUser(Long id);

    Integer countView(Long id);
}
