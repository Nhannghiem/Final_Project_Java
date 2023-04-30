package com.example.demo.model;

import org.springframework.data.relational.core.sql.In;

public interface CountData {

    Integer getCounts();
    Integer getCountItem();

    Integer getCountUser();

    Integer getCountCategory();
}
