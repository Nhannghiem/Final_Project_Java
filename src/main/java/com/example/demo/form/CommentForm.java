package com.example.demo.form;

import com.example.demo.entity.PostEntity;

import lombok.Data;


import java.time.LocalDateTime;


@Data
public class CommentForm {

    private Long id;

    private String content;


    private LocalDateTime createAt;

    private String photo;


    private String accountName;


    private PostEntity post;



}
