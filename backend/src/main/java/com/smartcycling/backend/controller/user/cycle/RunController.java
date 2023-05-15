package com.smartcycling.backend.controller.user.cycle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartcycling.backend.mapper.ParkinglotMapper;
import com.smartcycling.backend.mapper.UserLocationMapper;
import com.smartcycling.backend.pojo.Parkinglot;
import com.smartcycling.backend.pojo.User;
import com.smartcycling.backend.pojo.Userlocation;
import com.smartcycling.backend.service.BaiduService;
import com.smartcycling.backend.service.impl.util.UserDetailsImpl;
import com.smartcycling.backend.utils.DistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运动中上报地理位置
 */
@RequestMapping("/user/cycle/run/")
@RestController
public class RunController {

    @Autowired
    private BaiduService baiduService;

    @Autowired
    private UserLocationMapper userLocationMapper;

    @Autowired
    private ParkinglotMapper parkinglotMapper;
    /**
     * 上报位置 app中15秒会上报一次
     *
     * @param routeId    路线id
     * @param data 请求参数，其中包含了经纬度和速度
     * @return
     */
    @PostMapping("{routeId}")
    public Map<String,String> run(@PathVariable("routeId") String routeId,@RequestParam Map<String,String> data) {//可以将RunParaVo改为Map进行使用
        //上报数据到鹰眼服务
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        routeId = routeId.substring(1,routeId.length()-1);
//        System.out.println("routeId:"+routeId);
        System.out.println("run_lat"+data.get("latitude"));
        System.out.println("run_lng"+data.get("longitude"));
        Boolean result = this.baiduService.uploadLocation(routeId, data);//测试成功！
        Map<String,String> map = new HashMap<>();
        if (result) {
            //异步更新自己的位置数据
            QueryWrapper<Userlocation> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("uid",user.getId());
            Userlocation userLocation = userLocationMapper.selectOne(queryWrapper);
            if(userLocation == null){
                userLocationMapper.insert(new Userlocation(null,user.getId(),data.get("longitude"),data.get("latitude"),new Date()));
            }else {
                userLocationMapper.updateById(new Userlocation(userLocation.getId(),user.getId(),data.get("longitude"),data.get("latitude"),new Date()));
            }
            map.put("error_message","success");
            return map;
        }
        map.put("error_message","error:百度地图-鹰眼服务上传地理位置失败");
        return map;
    }

    /**
     * data包括：终点位置经纬度坐标：lng,lat
     * @param data
     * @return
     */
    @PostMapping("search")
    public Map<String,String> searchEndPlace(@RequestParam Map<String,String> data){

        String latitude = data.get("latitude");
        String longitude = data.get("longitude");
//        QueryWrapper<Parkinglot> queryWrapper =new QueryWrapper<>();
//        queryWrapper.eq("pid",1);
        System.out.println("run_search_lat："+data);
        List<Parkinglot> parkinglots = parkinglotMapper.selectList(null);//查询所有数据
//        System.out.println(parkinglots.toArray()[0]);
        double min_distance = 1e9;
        String min_lat = null;
        String min_lng = null;
        int Parksize = 0;
        for(Parkinglot parkinglot : parkinglots){
//            System.out.println("parkinglot_lat"+parkinglot.getPointY());
            double distance = DistanceUtils.getDistance(longitude,latitude,parkinglot.getPointX(),parkinglot.getPointY());
            if(parkinglot.getSize() > 0 && distance < min_distance){
                min_distance = distance;
                min_lat = parkinglot.getPointY();
                min_lng = parkinglot.getPointX();
                Parksize = parkinglot.getSize();
            }
        }

        Map<String,String> map = new HashMap<>();
        if(min_lat == null){
            map.put("error_message","附近的停车区域已满，请下次提前预定停车区域");
            return map;
        }
        map.put("size", String.valueOf(Parksize));
        map.put("lng",min_lng);
        map.put("lat",min_lat);

        map.put("error_message","success");
        System.out.println("size:-->"+Parksize);
        return map;
    }

}

