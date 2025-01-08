package com.az.taskmasterbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/unsecured")
    public String unsecured() {
        return "unsecured endpoint.";
    }

    @GetMapping("/secured")
    public String secured() {
        return "secured endpoint.";
    }
}
