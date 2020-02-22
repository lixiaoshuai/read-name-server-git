package com.tuspass.realname.controller;

import com.alibaba.fastjson.JSON;
import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.dto.RideDto;
import com.tuspass.realname.repository.entity.generate.Ride;
import com.tuspass.realname.server.RideServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/20 23:59
 **/
@RestController
@RequestMapping("/rideBus")
public class RideBusController {

    private Logger LOGGER = LoggerFactory.getLogger(RideBusController.class);
    @Autowired
    private RideServer rideServer;

    @PostMapping("/add")
    public RespEntity add(HttpServletRequest request , @RequestBody Ride ride) {
        LOGGER.info("添加乘车人信息请求，{}",ride);

        RespEntity respEntity = rideServer.add(ride);

        LOGGER.info("添加乘车人信息响应，{}", JSON.toJSONString(respEntity));
        return respEntity;
    }


    @PostMapping("/select")
    public RespEntity select(HttpServletRequest request , @RequestBody RideDto ride) {
        LOGGER.info("查看乘车人信息请求，{}",ride);

        RespEntity respEntity = rideServer.selectPage(ride);

        LOGGER.info("查看乘车人信息响应，{}", JSON.toJSONString(respEntity));
        return respEntity;
    }
}
