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
public class Route {

    @TableId(type= IdType.AUTO)//id自增
    private Integer rid; //主键id
    private String title; //路线标题
    private Integer uid; //创建路线的用户id     ！！原来为Long类型！！
    private Integer cid; // 自行车对应的id
    private Boolean is_share; //是否投稿
    private Integer size; //轨迹点数量
    private Double distance; //此段轨迹的里程数，单位：米
    private String time; //运动时间，格式：mm:ss
    private Double speed; //平均速度，单位：km/h
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date starttime; //创建路线时间戳
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date endttime; //结束路线时间戳

    private String start_point_x; //起点信息 x:经度
    private String start_point_y; //起点信息 y:纬度
    private Long start_point_localtime; //起点信息 位时间
    private Double start_point_speed; //起点信息 速度 单位：千米/小时 默认为0

    private String end_point_x; //终点信息 x:经度
    private String end_point_y; //终点信息 y:纬度
    private Long end_point_localtime; //终点信息 位时间
    private Double end_point_speed; //终点信息 速度 单位：千米/小时 默认为0
    private Integer status; //状态，1：开始，0：已结束
// 可以再写一个历史轨迹点表记录
//    private List<LocationPoint> points; //历史轨迹点列表

    //路线的位置，使用起点坐标作为路线坐标
//    private BigDecimal location_x; //x:经度
//    private BigDecimal location_y; //y:纬度


//    @Transient //不存储到MongoDB，仅用于前端显示
//    private Double routeDistance; //路线与我的距离


}
