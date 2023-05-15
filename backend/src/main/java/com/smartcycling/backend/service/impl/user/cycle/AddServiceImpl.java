package com.smartcycling.backend.service.impl.user.cycle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartcycling.backend.mapper.CycleMapper;
import com.smartcycling.backend.pojo.Cycle;
import com.smartcycling.backend.pojo.User;
import com.smartcycling.backend.service.impl.util.UserDetailsImpl;
import com.smartcycling.backend.service.user.cycle.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AddServiceImpl implements AddService {

    @Autowired
    private CycleMapper cycleMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser =(UserDetailsImpl)authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String cname = data.get("cname");
        String ctype = data.get("ctype");
        Map<String,String> map = new HashMap<>();
        QueryWrapper<Cycle> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("uid",user.getId());
        List<Cycle> list = cycleMapper.selectList(queryWrapper);
        for(Cycle cycle : list)
        {
            if(cycle.getCname().equals(cname))
            {
                map.put("error_message","您的车库中已经有一个名为: "+cname+" 的车辆了");
                return map;
            }
        }

        if(cname == null || cname.length() == 0)
        {
            map.put("error_message","车辆的名称不能为空");
            return map;
        }

        if(cname.length() > 100)
        {
            map.put("error_message","车辆的名称不能超过100个字符");
            return map;
        }


        System.out.println();

        if(ctype == null || ctype.length() == 0)
        {
            map.put("error_message","车辆的类型不能为空");
            return map;
        }

        if(ctype.length() > 25)
        {
            map.put("error_message","车辆的类型不能超过25个字符");
            return map;
        }

        Date now = new Date();
        Cycle cycle = new Cycle(null,user.getId(),cname,ctype,"未使用",now,now);
        cycleMapper.insert(cycle);

        map.put("error_message","success");

        return map;
    }
}
