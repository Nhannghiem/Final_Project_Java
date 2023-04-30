package com.example.demo.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResourceResponse <T> extends ResponseEntity<T> {
//    public ResourceResponse(HttpStatus status) {
//        super(status);
//    }


//    public ResourceResponse(T body, HttpStatus status) {
//        super(body, status);
//    }

    public ResourceResponse(T body) {
        super(body, HttpStatus.OK);
    }
//
//    public ResourceResponse(void deleteSoftPost) {
//        super(HttpStatus.OK);
//    }
}
