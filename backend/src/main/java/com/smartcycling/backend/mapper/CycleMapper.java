package com.smartcycling.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcycling.backend.pojo.Cycle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CycleMapper extends BaseMapper<Cycle> {
}
