package com.backend.assignment.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserUtil {
    private UserUtil(){
    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+ responseMessage + "\"}",httpStatus);
    }
}
