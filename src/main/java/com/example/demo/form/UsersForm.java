package com.example.demo.form;

import lombok.Data;



@Data
public class UsersForm {

    private Long id;

    private String accountName;

    private String email;

    private String password;

    private String photo;

    private String confirm;

    private String role;

}
