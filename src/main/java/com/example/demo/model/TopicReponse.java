package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicReponse {

    private Long id;

    private String content;

    private String title;

    private String createAt;

}
