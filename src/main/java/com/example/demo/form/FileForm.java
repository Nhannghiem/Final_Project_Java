package com.example.demo.form;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
public class FileForm {

    private Long id;


    private String fileName;


    private String fileType;

    @Lob
    private byte[] data;
}
