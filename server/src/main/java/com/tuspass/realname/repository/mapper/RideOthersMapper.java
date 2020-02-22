package com.tuspass.realname.repository.mapper;

import com.tuspass.realname.repository.entity.generate.RideOthers;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;

public interface RideOthersMapper extends Mapper<RideOthers>, ConditionMapper<RideOthers> {
}