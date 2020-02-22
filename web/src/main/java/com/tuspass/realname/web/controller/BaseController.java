package com.tuspass.realname.web.controller;


import com.alibaba.fastjson.JSON;
import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.common.utils.SignatureUtil;
import com.tuspass.realname.web.dto.redis.UserInfo;
import com.tuspass.realname.web.support.RedisSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author lxs
 * @Description
 * @Date 2019/12/3 10:55
 **/

@Component
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Value("${miniprogram.requestKey}")
    private String requestKey;

    @Value("${real.name.url}")
    protected  String payBaseUrl;
    @Autowired
    protected RedisSupport redisSupport;
    public ResponseEntity request(String url, String json,String token){
        try {

            logger.info("调用接口url:{},参数：{}",url,json);
            SimpleClientHttpRequestFactory c = new SimpleClientHttpRequestFactory();
            c.setConnectTimeout(30000);
            c.setReadTimeout(30000);
            RestTemplate restTemplate = new RestTemplate(c);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.valueOf("application/json;UTF-8"));
            requestHeaders.add("TOKEN",token);
            HttpEntity<String> httpEntity = new HttpEntity<>( new String(json.getBytes("UTF-8"),"utf-8"),requestHeaders);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
            logger.info("接口返回信息：{}", JSON.toJSONString(responseEntity.getBody()));


            return responseEntity;

        } catch (UnsupportedEncodingException e) {
            logger.error("接口调用异常，{}",e);
        }
        return null;
    }

    /**
     *  解析数据
     * @param url
     * @param body
     * @return
     */
    public RespEntity getRespEntity(String url , String body,String token){

        ResponseEntity responseEntity = request(url,body ,token);
        if(responseEntity == null){
            return RespEntity.errorResp("系统异常，请稍后再试");
        }
        String reqBody = (String)responseEntity.getBody();
        if(StringUtils.isEmpty(reqBody)){
            return RespEntity.successResp(null);
        }
        RespEntity respEntity = JSON.parseObject((String)responseEntity.getBody(),RespEntity.class);
        return respEntity;

    };

    public UserInfo getUserId(HttpServletRequest request){
        if(request ==null){
            return null;
        }
        String token = request.getHeader("TOKEN");
        return redisSupport.getUserInfo(token);
    }

    public UserInfo getUserId(String token){
        if(token ==null){
            return null;
        }
        return redisSupport.getUserInfo(token);
    }


    public Map getMap(HttpServletRequest request, String body){
        logger.info("接收请求参数，{}",body);
        Map map = JSON.parseObject(body,Map.class);
        map.put("openId",getUserId(request).getOpenId());
        return map;
    }


    /**
     * 计算签名
     * @param url
     * @param body
     * @return
     */
    public RespEntity requestSign(String url ,String body){
        Map map = JSON.parseObject(body,Map.class);
        String md5 = SignatureUtil.generateSign(map,requestKey);
        map.put("sign",md5);
        return getRespEntity(url, JSON.toJSONString(map),null);
    }

    /**
     * 计算签名
     * @param url
     * @param body
     * @return
     */
    public RespEntity requestSign(String url ,String body,String token){
        Map map = JSON.parseObject(body,Map.class);
        String md5 = SignatureUtil.generateSign(map,requestKey);
        map.put("sign",md5);
        return getRespEntity(url, JSON.toJSONString(map),token);
    }
}
