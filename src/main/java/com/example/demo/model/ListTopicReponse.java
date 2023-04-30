package com.example.demo.model;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ListTopicReponse {


    private Long id;

    private String email;

    private String content;

    private String title;

    private LocalDateTime creatAt;

    private Integer countView;

    private Integer contributions;

    private Long userId;
}
