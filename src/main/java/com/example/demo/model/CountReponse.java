package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CountReponse {

    private Integer counts;

    private Integer countItem;


    private Integer countUser;

    private Integer countCategory;
}
