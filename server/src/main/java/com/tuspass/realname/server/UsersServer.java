package com.tuspass.realname.server;

import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.repository.entity.generate.Users;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 10:04
 **/
public interface UsersServer {

    RespEntity add(Users users);

    RespEntity selectByPhone(String openId);

    RespEntity login(Users users);
}
