package com.example.demo.form;

import com.example.demo.entity.UsersEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryForm {

    private Long id;

    @NotBlank(message = "cannot be null or empty")
    private String content;

    @NotBlank(message = "cannot be null or empty")
    private String title;

    private UsersEntity systemManagement;
}
