package com.tuspass.realname.web.support;

import com.alibaba.fastjson.JSON;
import com.tuspass.realname.common.constant.RedisConstans;
import com.tuspass.realname.web.dto.redis.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author lxs
 * @Description
 * @Date 2019/12/3 15:59
 **/
@Component
public class RedisSupport {

    /**
     * token 过期时间
     */
    @Value("${redis.token.out.time}")
    public long tokenOutTime;

    @Resource
    private RedisTemplate redisTemplate;


    public UserInfo getUserInfo(String key){
        return JSON.parseObject((String)redisTemplate.opsForValue().get(RedisConstans.TOKEN+key),UserInfo.class);
    }

    public  void setUserInfo(String key, String userInfo){
        redisTemplate.opsForValue().set(RedisConstans.TOKEN+key,userInfo,tokenOutTime, TimeUnit.SECONDS);
    }

}
