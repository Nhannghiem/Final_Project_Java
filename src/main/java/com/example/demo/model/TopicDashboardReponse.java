package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicDashboardReponse {

    private String createAt;

    private Integer counts;
}
