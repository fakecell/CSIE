package com.smartcycling.backend.service.impl.user.cycle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartcycling.backend.mapper.CycleMapper;
import com.smartcycling.backend.pojo.Cycle;
import com.smartcycling.backend.pojo.User;
import com.smartcycling.backend.service.impl.util.UserDetailsImpl;
import com.smartcycling.backend.service.user.cycle.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListServiceImpl implements GetListService {

    @Autowired
    private CycleMapper cycleMapper;

    @Override
    public List<Cycle> getList() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        System.out.println("---------GetList---------");
        System.out.println("username:"+user.getUsername());
        QueryWrapper<Cycle>queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("uid",user.getId());//驼峰命名的时候用_
        return cycleMapper.selectList(queryWrapper);
    }
}
