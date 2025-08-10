package com.wassupshoppingmall.ping;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/__ping")
    public String ok() { return "ok"; }

    @GetMapping("/")
    public String root() { return "root-ok"; }
}