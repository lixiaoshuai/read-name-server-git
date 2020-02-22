package com.tuspass.realname.server;

import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.dto.RideDto;
import com.tuspass.realname.repository.entity.generate.Ride;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 10:05
 **/
public interface RideServer {

    RespEntity add(Ride ride);


    RespEntity selectPage(RideDto dto);
}
