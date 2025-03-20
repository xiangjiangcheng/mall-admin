package com.river.malladmin.system.controller;

import com.river.malladmin.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "测试")
    @GetMapping("/hello-world")
    public Result<String> test() {
        return Result.success("hello-world");
    }

}
