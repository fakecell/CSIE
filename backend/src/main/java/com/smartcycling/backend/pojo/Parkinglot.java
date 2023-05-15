package com.smartcycling.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 自定义点：可停车的地点
 * 通过单片机上传地点位置
 * --> 停车场是否需要一个记录信息的类：ParkingLotInfo ？？？
 */
public class Parkinglot {
    @TableId(type= IdType.AUTO)//id自增
    private Integer pid;//id 主键
    private Integer size;//停车场剩余车位
    private String pointX;//经度
    private String pointY;//纬度
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updateTime;
}
