package com.example.demo.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class ReplyForm {

    private Long id;

    private String content;


    private LocalDateTime createAt;


    private String photo;

    private String accountName;
}
