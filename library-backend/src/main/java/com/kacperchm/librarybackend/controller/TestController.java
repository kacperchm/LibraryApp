package com.kacperchm.librarybackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/global")
    public String getGlobal() {
        return "Tu każdy ma dostęp";
    }


    @GetMapping("/forUser")
    public String getUser() {
        return "Tu user ma dostęp";
    }

    @GetMapping("/forAdmin")
    public String getAdmin() {
        return "Tu admin ma dostęp";
    }
}
