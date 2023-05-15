package com.smartcycling.backend.controller.user.cycle;

import com.smartcycling.backend.pojo.Cycle;
import com.smartcycling.backend.service.user.cycle.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListController {
    @Autowired
    private GetListService getListService;

    @GetMapping("/user/cycle/getList/")
    public List<Cycle> getList()
    {
        return getListService.getList();
    }
}
