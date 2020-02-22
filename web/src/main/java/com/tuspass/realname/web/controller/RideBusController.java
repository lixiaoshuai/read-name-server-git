package com.tuspass.realname.web.controller;

import com.alibaba.fastjson.JSON;
import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.web.constant.Urls;
import com.tuspass.realname.web.dto.RideDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/20 23:59
 **/
@RestController
@RequestMapping("/rideBus")
public class RideBusController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RideBusController.class);


    @Value("${real.name.url}")
    private String realNameUrl;


    @Value("${ride.bus.url}")
    private String rideBusUrl;
    @PostMapping("/add")
    public RespEntity add(HttpServletRequest request, @RequestBody RideDto ride){
        LOGGER.info("用户添加乘车人请求{}", JSON.toJSONString(ride));
        String url = realNameUrl+ Urls.RIDEBUS_ADD;
        ride.setOpenId(getUserId(request).getOpenId());
        RespEntity respEntity = requestSign(url,JSON.toJSONString(ride));
        if(respEntity.isOk()){
            // TODO  转发自己服务
            sendRideBus(ride);
        }
        LOGGER.info("用户添加乘车人结束{}", JSON.toJSONString(ride));
        return respEntity;
    }


    @PostMapping("/select")
    public RespEntity select(HttpServletRequest request ,@RequestBody RideDto body){
        LOGGER.info("查看乘车记录请求{}", JSON.toJSONString(body));
        String url = realNameUrl+ Urls.RIDEBUS_SELECT;
        body.setOpenId(getUserId(request).getOpenId());
        RespEntity respEntity = requestSign(url,JSON.toJSONString(body));

        LOGGER.info("查看乘车记录结束{}", JSON.toJSONString(body));
        return respEntity;
    }


    /**
     * 转发交易到自己的服务
     *    private String cityCode;
     *     private String cityName;
     *     private String lineName;
     *     private String carType;
     *     private String plateNo;
     *     private String carriageNo;
     *     private String stationName;
     *     private String phone;
     *     private String cerType;
     *     private String cerNo;
     *     private String name;
     *     private Date submitTime;
     * @param ride
     */
    private void sendRideBus(RideDto ride){

        try {
            Map map = new HashMap();
            map.put("plateNo",ride.getVehicleId());
            map.put("phone",ride.getOthersPhone());
            map.put("submitTime",new Date());

            // TODO  地址需要问宋向阳要
            String url =rideBusUrl+"/";
            getRespEntity(url,JSON.toJSONString(map),null);
        } catch (Exception e) {
            LOGGER.error("转发乘车记录异常,{}",e.getMessage());
        }
    }

}
