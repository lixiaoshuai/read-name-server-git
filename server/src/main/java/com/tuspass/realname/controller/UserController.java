package com.tuspass.realname.controller;

import com.alibaba.fastjson.JSON;
import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.repository.entity.generate.Users;
import com.tuspass.realname.server.UsersServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/20 23:46
 **/
@RestController
@RequestMapping("/users")
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UsersServer usersServer;

    @PostMapping("/login")
    public RespEntity login(HttpServletRequest request, @RequestBody Users users){
        LOGGER.info("添加用户请求，{}", JSON.toJSONString(users));
        RespEntity respEntity = usersServer.add(users);

        LOGGER.info("添加用户请求，{}", JSON.toJSONString(respEntity));
        return respEntity;
    }


    @PostMapping("/{openId}")
    public RespEntity select(HttpServletRequest request, @PathVariable String openId){
        LOGGER.info("查看用户详情请求，{}", openId);
        RespEntity respEntity = usersServer.selectByPhone(openId);

        LOGGER.info("查看用户详情请求，{}", JSON.toJSONString(respEntity));
        return respEntity;
    }
}
