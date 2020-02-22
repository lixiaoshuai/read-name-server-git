package com.tuspass.realname.repository.mapper;

import com.tuspass.realname.repository.entity.generate.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UsersMapper extends Mapper<Users>, ConditionMapper<Users> {


    @Select({
            "select open_id as openId , phone, create_time createTime from users where phone = #{phone}"
    })
    List<Users> selectByPhone(@Param("phone") String phone);
}