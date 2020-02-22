package com.tuspass.realname.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RideDto  implements Serializable {

    private Integer pageSize;

    private Integer pageNum;

    private String id;
    /**
     * 用户编号
     */
    private String openId;

    /**
     * 自己
     */
    private String selfPhone;

    /**
     * 乘车人
     */
    private String othersPhone;

    /**
     * 类型，1-本人，2-替别人
     */
    private Integer type;

    /**
     * 车辆编号
     */
    private String vehicleId;

    /**
     * 线路号
     */
    private String lineId;

    /**
     * 线路名称
     */
    private String lineName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 接收时间
     */

    private Date acceptTime;

    private static final long serialVersionUID = 1L;
}
