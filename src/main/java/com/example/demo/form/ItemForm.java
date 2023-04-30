package com.example.demo.form;

import com.example.demo.entity.UsersEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ItemForm {

    private Long id;


    private String content;

    @NotBlank(message = "cannot be null or empty")
    private String title;

    private UsersEntity systemManagement;


}
