package com.example.demo.form;

import com.example.demo.entity.TopicEntity;

import com.example.demo.entity.UsersEntity;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PostForm {

    private Long id;

    private String content;

    private String title;

    private Integer isActive;

    private LocalDateTime createAt;

    private Date editingDate;

    private TopicEntity topic;

    private UsersEntity users;


}
