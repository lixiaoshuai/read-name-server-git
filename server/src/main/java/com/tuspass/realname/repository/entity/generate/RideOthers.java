package com.tuspass.realname.repository.entity.generate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "ride_others")
public class RideOthers implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 用户编号
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 自己
     */
    @Column(name = "self_phone")
    private String selfPhone;

    /**
     * 乘车人
     */
    @Column(name = "others_phone")
    private String othersPhone;

    /**
     * 类型，1-本人，2-替别人
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 车辆编号
     */
    @Column(name = "vehicle_id")
    private String vehicleId;

    /**
     * 线路号
     */
    @Column(name = "line_id")
    private String lineId;

    /**
     * 线路名称
     */
    @Column(name = "line_name")
    private String lineName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 接收时间
     */
    @Column(name = "accept_time")
    private Date acceptTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    public RideOthers withId(String id) {
        this.setId(id);
        return this;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户编号
     *
     * @return open_id - 用户编号
     */
    public String getOpenId() {
        return openId;
    }

    public RideOthers withOpenId(String openId) {
        this.setOpenId(openId);
        return this;
    }

    /**
     * 设置用户编号
     *
     * @param openId 用户编号
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取自己
     *
     * @return self_phone - 自己
     */
    public String getSelfPhone() {
        return selfPhone;
    }

    public RideOthers withSelfPhone(String selfPhone) {
        this.setSelfPhone(selfPhone);
        return this;
    }

    /**
     * 设置自己
     *
     * @param selfPhone 自己
     */
    public void setSelfPhone(String selfPhone) {
        this.selfPhone = selfPhone;
    }

    /**
     * 获取乘车人
     *
     * @return others_phone - 乘车人
     */
    public String getOthersPhone() {
        return othersPhone;
    }

    public RideOthers withOthersPhone(String othersPhone) {
        this.setOthersPhone(othersPhone);
        return this;
    }

    /**
     * 设置乘车人
     *
     * @param othersPhone 乘车人
     */
    public void setOthersPhone(String othersPhone) {
        this.othersPhone = othersPhone;
    }

    /**
     * 获取类型，1-本人，2-替别人
     *
     * @return type - 类型，1-本人，2-替别人
     */
    public Integer getType() {
        return type;
    }

    public RideOthers withType(Integer type) {
        this.setType(type);
        return this;
    }

    /**
     * 设置类型，1-本人，2-替别人
     *
     * @param type 类型，1-本人，2-替别人
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取车辆编号
     *
     * @return vehicle_id - 车辆编号
     */
    public String getVehicleId() {
        return vehicleId;
    }

    public RideOthers withVehicleId(String vehicleId) {
        this.setVehicleId(vehicleId);
        return this;
    }

    /**
     * 设置车辆编号
     *
     * @param vehicleId 车辆编号
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * 获取线路号
     *
     * @return line_id - 线路号
     */
    public String getLineId() {
        return lineId;
    }

    public RideOthers withLineId(String lineId) {
        this.setLineId(lineId);
        return this;
    }

    /**
     * 设置线路号
     *
     * @param lineId 线路号
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 获取线路名称
     *
     * @return line_name - 线路名称
     */
    public String getLineName() {
        return lineName;
    }

    public RideOthers withLineName(String lineName) {
        this.setLineName(lineName);
        return this;
    }

    /**
     * 设置线路名称
     *
     * @param lineName 线路名称
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    public RideOthers withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取接收时间
     *
     * @return accept_time - 接收时间
     */
    public Date getAcceptTime() {
        return acceptTime;
    }

    public RideOthers withAcceptTime(Date acceptTime) {
        this.setAcceptTime(acceptTime);
        return this;
    }

    /**
     * 设置接收时间
     *
     * @param acceptTime 接收时间
     */
    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RideOthers other = (RideOthers) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getSelfPhone() == null ? other.getSelfPhone() == null : this.getSelfPhone().equals(other.getSelfPhone()))
            && (this.getOthersPhone() == null ? other.getOthersPhone() == null : this.getOthersPhone().equals(other.getOthersPhone()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getVehicleId() == null ? other.getVehicleId() == null : this.getVehicleId().equals(other.getVehicleId()))
            && (this.getLineId() == null ? other.getLineId() == null : this.getLineId().equals(other.getLineId()))
            && (this.getLineName() == null ? other.getLineName() == null : this.getLineName().equals(other.getLineName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getAcceptTime() == null ? other.getAcceptTime() == null : this.getAcceptTime().equals(other.getAcceptTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getSelfPhone() == null) ? 0 : getSelfPhone().hashCode());
        result = prime * result + ((getOthersPhone() == null) ? 0 : getOthersPhone().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getVehicleId() == null) ? 0 : getVehicleId().hashCode());
        result = prime * result + ((getLineId() == null) ? 0 : getLineId().hashCode());
        result = prime * result + ((getLineName() == null) ? 0 : getLineName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getAcceptTime() == null) ? 0 : getAcceptTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", openId=").append(openId);
        sb.append(", selfPhone=").append(selfPhone);
        sb.append(", othersPhone=").append(othersPhone);
        sb.append(", type=").append(type);
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", lineId=").append(lineId);
        sb.append(", lineName=").append(lineName);
        sb.append(", createTime=").append(createTime);
        sb.append(", acceptTime=").append(acceptTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}