package com.river.malladmin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JiangCheng Xiang
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Tag(name = "02.Test Apis")
public class TestController {

    @GetMapping("/hello-world")
    public String test() {
        return "hello-world";
    }

}
