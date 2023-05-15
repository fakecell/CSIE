package com.smartcycling.backend.service.impl.user.cycle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartcycling.backend.mapper.CycleMapper;
import com.smartcycling.backend.pojo.Cycle;
import com.smartcycling.backend.pojo.User;
import com.smartcycling.backend.service.impl.util.UserDetailsImpl;
import com.smartcycling.backend.service.user.cycle.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private CycleMapper cycleMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();
        int cycle_id = Integer.parseInt(data.get("cycle_id"));

        Map<String,String> map = new HashMap<>();
        Cycle cycle = cycleMapper.selectById(cycle_id);

        if(cycle == null)
        {
            map.put("error_message","车辆不存在或已被删除");
            return map;
        }
        if(!cycle.getUid().equals(user.getId()))
        {
            map.put("error_message","您没有权限修改该车辆");
            return map;
        }

        String cname = data.get("cname");
        String ctype = data.get("ctype");

        QueryWrapper<Cycle> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("uid",user.getId());
        List<Cycle> list = cycleMapper.selectList(queryWrapper);
        for(Cycle c : list)
        {
            if(c.getCname().equals(cname))
            {
                map.put("error_message","您的车库中已经有一个名为: "+cname+" 的车辆了");
                return map;
            }
        }

        Cycle new_cycle = new Cycle(
                cycle.getId(),
                cycle.getUid(),
                cname,
                ctype,
                cycle.getCstate(),
                cycle.getCreatetime(),
                new Date()
        );

        cycleMapper.updateById(new_cycle);

        map.put("error_message","success");
        return map;

    }
}
