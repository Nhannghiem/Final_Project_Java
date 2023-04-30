package com.example.demo.form;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.UsersEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class TopicForm {


    private Long id;

    @NotBlank(message = "cannot be null or empty")
    private String content;

    @NotBlank(message = "cannot be null or empty")
    private String title;


    private LocalDateTime creatAt;


    private Integer IsActive;

    private CategoryEntity category;

//    private UserEntity user;

    private UsersEntity systemManagement;


}
