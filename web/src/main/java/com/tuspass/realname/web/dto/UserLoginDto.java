package com.tuspass.realname.web.dto;

import lombok.Data;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 2:42
 **/
@Data
public class UserLoginDto {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 小程序登录code/短信验证码
     */
    private String code;
    /**
     * 小程序用户信息加密数据
     */
    private String encryptedData;
    /**
     * 加密算法的初始向量
     */
    private String iv;
}
