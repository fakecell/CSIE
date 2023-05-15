package com.smartcycling.backend.controller.user.cycle;

import com.smartcycling.backend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 路线相关的业务
 */
@RestController
@RequestMapping("/user/cycle/route/")
public class RouteController {

    @Autowired
    private RouteService routeService;

    /**
     * 创建路线
     * 一个是在本地mongodb数据库中创建路线，一个是在百度鹰眼服务中创建一个entity
     *
     * @return
     */
    @PostMapping
    public Map<String,String> createRoute(@RequestParam Map<String,String> data) {
        String routeId = this.routeService.createRoute(data);
        Map<String,String> map = new HashMap<>();
        if (null != routeId) {
            //创建成功
            map.put("routeId",routeId);
            map.put("error_message","success");
            return map;
        }
        //创建失败

        map.put("error_message","创建路线失败");
        return map;
    }

//    /**
//     * 删除路线
//     *
//     * @param routeId
//     * @return
//     */
//    @DeleteMapping("{routeId}")
//    public Map<String,String> deleteRoute(@PathVariable("routeId") String routeId) {
//        Boolean result = this.routeService.deleteRoute(routeId);
//        Map<String,String> map = new HashMap<>();
//        if (result) {
//            //删除成功
//            map.put("error_message","success");
//            return map;
//        }
//        //删除失败
//        map.put("error_message","删除路线失败");
//        return map;
//    }
//
//    /**
//     *更新路线（结束运动）
//     * @param routeId 路线ID
//     * @param title  路线标题
//     * @return
//     */
//    @PutMapping
//    public Object updateRoute(@RequestParam ("routeId") String routeId,@RequestParam("title") String title){
//        return this.routeService.updateRoute(routeId,title);
//    }

}
