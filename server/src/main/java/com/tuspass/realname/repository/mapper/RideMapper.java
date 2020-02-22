package com.tuspass.realname.repository.mapper;

import com.tuspass.realname.repository.entity.generate.Ride;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;

public interface RideMapper extends Mapper<Ride>, ConditionMapper<Ride> {
}