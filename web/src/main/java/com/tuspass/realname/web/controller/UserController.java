package com.tuspass.realname.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.common.enums.ResultEnum;
import com.tuspass.realname.web.constant.Urls;
import com.tuspass.realname.web.dto.UserLoginDto;
import com.tuspass.realname.web.dto.redis.UserInfo;
import com.tuspass.realname.web.exception.BusinessException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/20 23:46
 **/
@RestController
public class UserController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Value("${miniprogram.appId}")
    private String appId;

    @Value("${miniprogram.appSecret}")
    private String appSecret;

    @Value("${real.name.url}")
    private String realNameUrl;

    /**
     * 登录授权
     * @param user
     * @return
     */
    @PostMapping("/login")
    public RespEntity login(@RequestBody UserLoginDto user){
        LOGGER.info("用户登录请求{}", JSON.toJSONString(user));

        UserInfo userInfo = new UserInfo();
        RespEntity respEntity = requestSign(realNameUrl+Urls.USERS_ADD,JSON.toJSONString((userInfo)));
        if(respEntity.isOk()){
            return RespEntity.successResp(getToken(userInfo));

        }
        LOGGER.info("用户登录结束{}", JSON.toJSONString(user));
        return respEntity;
    }


    /**
     *  获取token
     * @param user
     * @return
     */
    @PostMapping("/token")
    public RespEntity token(@RequestBody UserLoginDto user){
        LOGGER.info("用户获取TOKNE请求{}", JSON.toJSONString(user));

        String url = realNameUrl+ Urls.USERS_SELECT+user.getPhone();
        RespEntity respEntity = requestSign(url,JSON.toJSONString(user));
        if(respEntity.isOk()){
            Map map = (Map) respEntity.getData();
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setOpenId(MapUtils.getString(map,"openId"));
            userInfo1.setPhone(MapUtils.getString(map,"phone"));
            return RespEntity.successResp(getToken(userInfo1));
        }
        LOGGER.info("用户获取TOKNE结束{}", JSON.toJSONString(respEntity));
        return respEntity;
    }
    /**
     *  解析登录请求，去微信获取openId
     *  解析 EncryptedData数据
     * @param userLoginDto
     * @return
     */
    public UserInfo getOpenId(UserLoginDto userLoginDto){


        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%1$s&secret=%2$s&js_code=%3$s&grant_type=authorization_code", appId, appSecret, userLoginDto.getCode());
        ResponseEntity<String> responseEntity  = restTemplate.getForEntity(url, String.class);
        String rspBody = responseEntity.getBody();
        LOGGER.info("微信小程序请求{}, 响应{}", url, rspBody);
        JSONObject jsonObject = JSONObject.parseObject(rspBody);

        int errCode = jsonObject.getIntValue("errcode");
        if (errCode != 0x00) {
            LOGGER.error("微信code2Session访问失败, 错误码{}", errCode);
            throw new BusinessException(ResultEnum.WX_MINI_PROGRAM_FAILED);
        }

        String sessionKey = jsonObject.getString("session_key");
        String openId = jsonObject.getString("openid");

        String phone = decryptUserInfo(userLoginDto.getEncryptedData(), userLoginDto.getIv(), sessionKey);

        if(StringUtils.isAnyEmpty(openId,phone)){
            LOGGER.warn("解密数据  openId:{},phone:{}",openId,phone);
            throw new BusinessException(ResultEnum.PARAMETER_ERROR);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openId);
        userInfo.setPhone(phone);
        return userInfo;

    }

    /**
     *  解密加密数据，得到手机号
     * @param encryptData
     * @param iv
     * @param sessionKey
     * @return
     */
    public String decryptUserInfo(String encryptData, String iv, String sessionKey) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] abyData = decoder.decode(encryptData);

            LOGGER.info("微信小程序敏感数据密文:{}, iv:{}, sessionKey:{}", encryptData, iv, sessionKey);
            SecretKeySpec key = new SecretKeySpec(decoder.decode(sessionKey), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(decoder.decode(iv)));
            String value = new String(cipher.doFinal(abyData));
            LOGGER.info("微信小程序敏感数据明文:{}", value);
            JSONObject jsonObject = JSONObject.parseObject(value);
            String phoneNumber = jsonObject.getString("phone");
            if (phoneNumber.startsWith("+")) {
                phoneNumber = phoneNumber.substring(1).replace(" ", "");
            }
            return phoneNumber;
        } catch (Exception e) {
            LOGGER.error("微信小程序解密用户信息失败,{}", e);
            throw new BusinessException(ResultEnum.PARAMETER_ERROR);
        }
    }

    /**
     *
     *  生成token 存放 reids 里面
     * @return
     */
    private Map getToken(UserInfo userInfo){
        String token = getUUID();
        redisSupport.setUserInfo(token,JSON.toJSONString(userInfo));
        LOGGER.info("生成token:{}",token);
        Map map = new HashMap();
        map.put("token",token);
        return map;
    }
    /**
     * 获取唯一 id
     * @return
     */
    private String getUUID(){
        while (true) {
            String guid = UUID.randomUUID().toString();
            if (getUserId(guid) == null) {
                return guid;
            }
        }
    }
}
