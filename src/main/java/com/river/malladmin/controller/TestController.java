package com.river.malladmin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JiangCheng Xiang
 */
@RestController
public class TestController {

    @GetMapping("/hello-world")
    public String test() {
        return "hello-world";
    }

}
