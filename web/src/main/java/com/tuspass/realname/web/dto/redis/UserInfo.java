package com.tuspass.realname.web.dto.redis;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 23:58
 **/
@Data
public class UserInfo implements Serializable {

    private String openId;

    private String phone;
}
