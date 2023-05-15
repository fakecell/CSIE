package com.smartcycling.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class RouteInfoService {

    @Autowired(required=false)
    private MongoTemplate mongoTemplate;

    @Autowired
    private BaiduService baiduService;
    /**
     * 更新路线数据，查询鹰眼服务中的轨迹数据，更新到MongoDB中
     * @param routeId
     * @param userId
     */
    @Async
    public CompletableFuture<String> updateRouteInfo(String routeId, Long userId) {

        //根据路线ID查询路线数据：time
//        Route route = this.mongoTemplate.findById(new ObjectId(routeId), Route.class);
//
//
//
//
//        Long startTime = route.getStartTime().getTime() / 1000; //开始时间，精确到秒
//        Long endTime = route.getEndTime().getTime() / 1000;
//        Route routeEntity = this.baiduService.queryEntity(routeId, startTime, endTime);
//        //计算运动时间
//        String time;
//
//        try {
//            time = TimeUtils.formatTime(routeEntity.getEndPoint().getLocTime() - routeEntity.getStartPoint().getLocTime());
//        } catch (Exception e) {
//            time = "00:00";
//        }
//
//        //计算平均速度：每个点速度总和 / 轨迹点总数
//        Double speed;
//        try {
//            speed = routeEntity.getPoints().stream().mapToDouble(LocationPoint::getSpeed).sum() / routeEntity.getSize();
//            speed = NumberUtil.round(speed,2).doubleValue();
//        } catch (Exception e) {
//            speed = 0.00;
//        }
//        if(routeEntity == null) {
//            return CompletableFuture.completedFuture("error");
//        }
//
//        Update update = Update.update("size",routeEntity.getSize())
//                .set("distance", NumberUtil.round(routeEntity.getDistance(),2).doubleValue())
//                .set("startPoint",routeEntity.getStartPoint())
//                .set("endPoint",routeEntity.getEndPoint())
//                .set("points",routeEntity.getPoints())
//                .set("location",new GeoJsonPoint(routeEntity.getStartPoint().getLongitude(),routeEntity.getStartPoint().getLatitude()))//方便找到附近的人的路线
//                .set("time",time)
//                .set("speed",speed);
//        //更新数据到MongoDB
//        Query query = Query.query(Criteria.where("id").is(routeId));
//        this.mongoTemplate.updateFirst(query,update,Route.class);

        return CompletableFuture.completedFuture("ok");
    }
}
