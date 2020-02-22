package com.tuspass.realname.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 10:50
 **/
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health(){
        return "ok";
    }

}
