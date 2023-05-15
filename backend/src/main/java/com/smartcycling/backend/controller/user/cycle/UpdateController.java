package com.smartcycling.backend.controller.user.cycle;

import com.smartcycling.backend.service.user.cycle.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateController {
    @Autowired
    private UpdateService updateService;

    @PostMapping("/user/cycle/update/")
    public Map<String,String> update(@RequestParam Map<String,String> data)
    {
        return updateService.update(data);
    }

}
