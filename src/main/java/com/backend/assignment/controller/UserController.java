package com.backend.assignment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/user")
public interface UserController {
    @PostMapping(path = "/signup")
    ResponseEntity<String> signUp(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/login")
    ResponseEntity<String> login(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/hello")
    ResponseEntity<String> sayHello();
}
