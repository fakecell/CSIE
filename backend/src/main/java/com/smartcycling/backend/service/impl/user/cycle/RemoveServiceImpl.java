package com.smartcycling.backend.service.impl.user.cycle;

import com.smartcycling.backend.mapper.CycleMapper;
import com.smartcycling.backend.pojo.Cycle;
import com.smartcycling.backend.pojo.User;
import com.smartcycling.backend.service.impl.util.UserDetailsImpl;
import com.smartcycling.backend.service.user.cycle.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class RemoveServiceImpl implements RemoveService {

    @Autowired
    private CycleMapper cycleMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int cycle_id = Integer.parseInt(data.get("cycle_id"));

        Cycle cycle = cycleMapper.selectById(cycle_id);

        Map<String,String> map = new HashMap<>();

        if(cycle == null)
        {
            map.put("error_message","车辆不存在或已被删除");
            return map;
        }
        if(!cycle.getUid().equals(user.getId()))
        {
            map.put("error_message","您没有权限删除该车辆");
            return map;
        }

        cycleMapper.deleteById(cycle_id);
        map.put("error_message","success");
        return map;


    }
}
