package com.tuspass.realname.dto;

import com.tuspass.realname.repository.entity.generate.Ride;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 19:38
 **/
@Data
public class RideDto extends Ride implements Serializable {

    private Integer pageSize;

    private Integer pageNum;
}
