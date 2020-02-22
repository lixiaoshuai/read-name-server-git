package com.tuspass.realname.server.impl;

import com.alibaba.fastjson.JSON;
import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.repository.entity.generate.Users;
import com.tuspass.realname.repository.mapper.UsersMapper;
import com.tuspass.realname.server.UsersServer;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 21:38
 **/
@Service
public class UsersServerImpl implements UsersServer {

    private Logger logger = LoggerFactory.getLogger(UsersServerImpl.class);
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public RespEntity add(Users users) {
        if(StringUtils.isAnyEmpty(users.getPhone(),users.getOpenId())){
            return RespEntity.errorResp("参数错误");
        }
        usersMapper.insert(users);
        return RespEntity.successResp();
    }

    @Override
    public RespEntity selectByPhone(String openId) {
        Users users = usersMapper.selectByPhone(openId).get(0);
        if(ObjectUtils.isEmpty(users)){
            return RespEntity.errorResp("用户不存在");
        }
        return RespEntity.successResp(users);
    }

    /**
     *  用户发送登录请求，如果用户存在，就不需要重新添加用户
     *
     * @param users
     * @return
     */
    @Override
    public RespEntity login(Users users) {

        RespEntity respEntity = selectByPhone(users.getOpenId());
        if(!respEntity.isOk()){
            return add(users);
        }else{
            logger.warn("用户已经存在，{}", JSON.toJSONString(users));
        }
        return RespEntity.successResp();
    }
}
