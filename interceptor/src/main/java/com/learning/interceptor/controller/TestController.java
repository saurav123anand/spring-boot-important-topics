package com.learning.interceptor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/greet")
    public String greet(){
        log.info("reaching to greet controller");
        return "good morning";
    }
}
