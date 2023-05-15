package com.smartcycling.backend.service.parkinglot;

import com.smartcycling.backend.mapper.ParkinglotMapper;
import com.smartcycling.backend.pojo.Parkinglot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InCycleService {
    @Autowired
    private ParkinglotMapper parkinglotMapper;

    public boolean inCycle(int pid){
//        QueryWrapper<Parkinglot> queryWrapper =new QueryWrapper<>();
//        queryWrapper.eq("uid",user.getId());//驼峰命名的时候用_
//        return cycleMapper.selectList(queryWrapper);
//        queryWrapper.eq("pid",pid);
        Parkinglot parkinglot = parkinglotMapper.selectById(pid);
        if(parkinglot == null){
            return false;
        }else if(parkinglot.getSize() > 0){
            Parkinglot parkinglot1 = new Parkinglot(
                    parkinglot.getPid(),
                    parkinglot.getSize() - 1,
                    parkinglot.getPointX(),
                    parkinglot.getPointY(),
                    parkinglot.getCreateTime(),
                    new Date()
            );
            parkinglotMapper.updateById(parkinglot1);
            return true;
        }
        return false;
    }



}
