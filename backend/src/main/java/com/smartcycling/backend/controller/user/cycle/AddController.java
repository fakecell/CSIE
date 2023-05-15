package com.smartcycling.backend.controller.user.cycle;

import com.smartcycling.backend.service.user.cycle.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddController {
    @Autowired
    private AddService addService;
    @PostMapping("/user/cycle/add/")
    public Map<String,String> add(@RequestParam Map<String,String> data)
    {
        System.out.println(data.toString());
        return addService.add(data);

    }
}
