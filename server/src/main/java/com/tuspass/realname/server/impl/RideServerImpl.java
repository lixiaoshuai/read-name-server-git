package com.tuspass.realname.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tuspass.realname.common.entity.PageEntity;
import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.dto.RideDto;
import com.tuspass.realname.repository.entity.generate.Ride;
import com.tuspass.realname.repository.mapper.RideMapper;
import com.tuspass.realname.repository.mapper.RideOthersMapper;
import com.tuspass.realname.server.RideServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 19:34
 **/
@Service
public class RideServerImpl implements RideServer {

    @Autowired
    private RideMapper rideMapper;
    @Autowired
    private RideOthersMapper rideOthersMapper;
    @Override
    public RespEntity add(Ride ride) {

//        String id = UniqueId.Transaction.generate();

        String id = UUID.randomUUID().toString().replace("-","");
        ride.setId(id);
        ride.setCreateTime(new Date());
         rideMapper.insert(ride);
//        rideOthersMapper.insert(ride);
        return RespEntity.successResp();
    }

    @Override
    public RespEntity selectPage(RideDto dto) {
        Page<Object> page = PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Ride> list = rideMapper.selectByExample(queryBuilder(dto));

        return PageEntity.successPage(list, page.getTotal(), "查询成功");
    }

    private Example queryBuilder(Ride dto){
        Example.Builder example = Example.builder(Ride.class);
        WeekendSqls<Ride> sqls = WeekendSqls.custom();

        if(StringUtils.isNotEmpty(dto.getOpenId())){
            sqls.andEqualTo(Ride::getOpenId,dto.getOpenId());
        }
        example.where(sqls);

        return example.build();
    }
}
