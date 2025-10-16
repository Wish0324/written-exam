package com.example.controller;

import com.example.common.Constant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shimmer
 * @date 2025/10/16
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/hello")
    public String hello() {
        return Constant.GREETING;
    }

    @GetMapping("login")
    public String login() {
        return Constant.SUCCESSFUL_LOGIN;
    }

}