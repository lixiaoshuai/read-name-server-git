package com.tuspass.realname.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.tuspass.realname.web.dto.redis.UserInfo;
import com.tuspass.realname.web.support.RedisSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GlobalInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Value("${miniprogram.requestKey}")
    private String requestKey;


    @Autowired
    RedisSupport redisSupport;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkToken(request, response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader("TOKEN");

        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String url = request.getRequestURI();
        if (url.contains("/token")|| url.contains("/login")) {
            return true;
        }

        if (StringUtils.isEmpty(token)) {
            LOGGER.info("未获取TOKEN，不允许操作");
            response.setStatus(450);
            return false;
        }



        /*Map<String, Object> parameterMap = JSON.parseObject(new BodyReaderHttpServletRequestWrapper(request).getBodyString(request), Map.class);
        LOGGER.info("请求url:{}, 请求参数:{}", url, JSONObject.toJSONString(parameterMap));

        if (!SignatureUtil.validateSign(Util.convertMap(parameterMap), requestKey)) {
            LOGGER.error("请求签名校验失败");
            throw new BusinessException(ResultEnum.PARAMETER_ERROR, "参数错误");
        }*/


        UserInfo openId = redisSupport.getUserInfo(token);
        if(null == openId){
            LOGGER.info("token {} 不存在",token);
            response.setStatus(450);
            return false;
        }

        redisSupport.setUserInfo(token, JSON.toJSONString(openId));
        LOGGER.info("TOKEN={}", token);

        response.setHeader("TOKEN", token);
        return true;
    }



}
