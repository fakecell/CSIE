package com.smartcycling.backend.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.smartcycling.backend.config.BaiduConfig;
import com.smartcycling.backend.pojo.Route;
import com.smartcycling.backend.pojo.User;
import com.smartcycling.backend.service.impl.util.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BaiduService {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static{
        //驼峰转化，因为pojo里startTime是驼峰，但是百度地图api中是start_time
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
    @Autowired
    private BaiduApiService baiduApiService;

    @Autowired
    private BaiduConfig baiduConfig;


    /**
     * 创建鹰眼服务中的实体
     *
     * @param routeId 路线id
     * @return
     */
    public Boolean createEntity(String routeId) {
        String url = this.baiduConfig.getUrl() + "/entity/add";
        return this.baiduApiService.execute(url, Method.POST,
                this.createParam(routeId), response -> {
                    if (response.isOk()) {
                        String body = response.body();
                        JSONObject jsonObject = JSONUtil.parseObj(body);
                        return jsonObject.getInt("status") == 0;
                    }
                    return false;
                });
    }

    /**
     * 构建创建实体的参数
     *
     * @param routeId
     * @return
     */
    private Map<String, Object> createParam(String routeId) {
        return MapUtil.builder("entity_name", this.createEntityName(routeId)).build();
    }

    /**
     * 创建实体名称
     *
     * @param routeId
     * @return
     */
    private Object createEntityName(String routeId) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser =(UserDetailsImpl)authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        System.out.println(routeId);
        System.out.println("route_" + Integer.parseInt(routeId) + "_" + user.getId());
        return "route_" + Integer.parseInt(routeId) + "_" + user.getId();
    }

    /**
     * 删除百度鹰眼服务中的实体
     *
     * @param routeId
     * @return
     */
    public boolean deleteEntity(String routeId) {
        String url = this.baiduConfig.getUrl() + "/entity/delete";
        return this.baiduApiService.execute(url, Method.POST,
                this.createParam(routeId), response -> {
                    if (response.isOk()) {
                        String body = response.body();
                        JSONObject jsonObject = JSONUtil.parseObj(body);
                        return jsonObject.getInt("status") == 0;
                    }
                    return false;
                });
    }

    /**
     * 给实体添加轨迹点
     *
     * @param routeId
     * @param data
     * @return
     */
    public Boolean uploadLocation(String routeId, Map<String,String> data) {
//        System.out.println(Double.parseDouble(data.get("latitude")));
//        System.out.println("uploadLocation"+routeId);
        System.out.println(routeId);
        String url = this.baiduConfig.getUrl() + "/track/addpoint";
        Map<String, Object> paramMap = MapUtil.builder(new HashMap<String, Object>())
                .put("entity_name", this.createEntityName(routeId))//this.createEntityName(routeId)
                .put("latitude", Double.parseDouble(data.get("latitude"))) //纬度
                .put("longitude", Double.parseDouble(data.get("longitude"))) //经度
                .put("loc_time", System.currentTimeMillis() / 1000) //定位时间戳，精确到秒
                .put("coord_type_input", "gcj02") //gcj02 坐标类型 app中使用的是腾讯地图
                .put("speed",Double.parseDouble(data.get("speed"))).build();//速度
//        System.out.println("-----------------paramMap-----------------");
//        System.out.println(paramMap);
        return this.baiduApiService.execute(url, Method.POST,
                paramMap, response -> {
                    if (response.isOk()) {
//                        System.out.println("isok");
                        String body = response.body();
                        JSONObject jsonObject = JSONUtil.parseObj(body);
//                        System.out.println(jsonObject);
                        return jsonObject.getInt("status") == 0;
                    }
                    return false;
                });
    }

    /**
     *
     * @param routeId 路线ID
     * @param startTime 开始时间 ： 单位：s
     * @param endTime 结束时间
     */
    public Route queryEntity(String routeId,Long startTime,Long endTime) {
        String url = this.baiduConfig.getUrl() + "/track/gettrack";
        Map<String, Object> paramMap = MapUtil.builder(new HashMap<String, Object>())
                .put("entity_name", this.createEntityName(routeId))
                .put("startTime", startTime)
                .put("endTime", endTime)
                .put("is_processed", 1)
                .put("coord_type_output", "gcj02")
                .build();
        return this.baiduApiService.execute(url,Method.GET,paramMap,response -> {
            if(response.isOk()){
                String body = response.body();//json
                try {
                    return MAPPER.readValue(body, Route.class);//反序列化
                } catch (Exception e) {
                    log.error("查询鹰眼服务出错，查询到的数据为：{}",body,e);
                }
            }
            return null;
        });





    }
}






















