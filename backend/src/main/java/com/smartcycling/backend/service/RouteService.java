package com.smartcycling.backend.service;

import com.smartcycling.backend.mapper.RouteMapper;
import com.smartcycling.backend.pojo.Route;
import com.smartcycling.backend.pojo.User;
import com.smartcycling.backend.service.impl.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class RouteService {

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private BaiduService baiduService;

//    @Autowired
//    private RouteInfoService routeInfoService;

    /**
     * 创建路线
     *
     * @return
     */
    public String createRoute(Map<String,String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser =(UserDetailsImpl)authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        System.out.println(user.getId());
        String longitude = data.get("longitude");
        String latitude = data.get("latitude");
        String cycle_id = data.get("cycle_id");
        System.out.println(latitude);
        System.out.println(longitude);
        int cid = Integer.parseInt(cycle_id);


        Route route = new Route(null,null,user.getId(),cid,false,0,0.0,null,0.0,new Date(),null,longitude,latitude,null,null,null,null,null,null,null);
//        route.setUid(user.getId());
//        route.setStatus(1);//开始骑行状态
//        route.setIsShare(false); //默认不分享
//        route.setStartTime(new Date());

        //将数据保存到Mysql数据库中
        this.routeMapper.insert(route);

        String routeId = route.getRid().toString();

        //百度地图鹰眼服务中创建Entity
        Boolean bool = this.baiduService.createEntity(routeId);
        if (bool) {
            //成功
            return routeId;
        }

        //失败
        return null;
    }

    /**
     * 删除路线
     *
     * @param routeId 路线id
     * @return
     */
//    public Boolean deleteRoute(String routeId) {
//        //删除MongoDB中的数据
//        Query query = this.createQuery(routeId);
//        boolean result = this.mongoTemplate.remove(query, Route.class).getDeletedCount() == 1;
//        if (result) {
//            //删除百度鹰眼服务中的实体
//            if (this.baiduService.deleteEntity(routeId)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 构造查询对象，设置了2个条件，其中userId是确保只删除自己的数据
//     *
//     * @param routeId
//     * @return
//     */
//    private Query createQuery(String routeId) {
//        return Query.query(Criteria.where("id").is(new ObjectId(routeId))
//                .and("userId").is(UserThreadLocal.get()));
//    }
//
//    /**
//     *更新路线（结束运动）
//     * @param routeId 路线ID
//     * @param title  路线标题
//     * @return
//     */
//    public Object updateRoute(String routeId, String title) {
//        //判断路线是否已经结束，如果已经结束就不能再次结束
//        Route route = this.queryRouteById(routeId);
//        if(null == route){
//            return ErrorResult.builder()
//                    .errCode("500")
//                    .errMessage("结束路线失败，路线不存在").build();
//        }
//        if(route.getStatus() == 0) {
//            return ErrorResult.builder()
//                    .errCode("500")
//                    .errMessage("结束路线失败，路线已经结束").build();
//        }
//        //已经正常结束，需要更新路线数据
//        Update update = Update.update("title",title)
//                .set("status",0)
//                .set("endTime",System.currentTimeMillis());
//        UpdateResult updateResult = this.mongoTemplate.updateFirst(this.createQuery(routeId), update, Route.class);
//        if(updateResult.getModifiedCount() == 1){
//            //更新成功，则查询百度地图鹰眼服务中的路线轨迹点，更新到路线数据中，异步的操作
//            this.routeInfoService.updateRouteInfo(routeId,UserThreadLocal.get());
//            //更新成功
//            return null;
//        }
//
//
//        return ErrorResult.builder()
//                .errCode("502")
//                .errMessage("结束路线失败").build();
//    }
//
//    /**
//     * 根据主键查询路线对象
//     * @param routeId
//     * @return
//     */
//    private Route queryRouteById(String routeId) {
//        return this.mongoTemplate.findById(new ObjectId(routeId),Route.class);
//    }
}
