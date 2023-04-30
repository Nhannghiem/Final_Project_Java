package com.example.demo.service;

import com.example.demo.model.FellingCountReponse;

public interface FellingService {

    void saveUserAndPst(Long id);


    FellingCountReponse countLike(Long id);


}
