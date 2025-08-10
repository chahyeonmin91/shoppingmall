package com.wassupshoppingmall.global.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/__ping")
    public String ok() {
        return "ok";
    }
}