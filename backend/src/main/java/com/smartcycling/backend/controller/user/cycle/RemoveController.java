package com.smartcycling.backend.controller.user.cycle;

import com.smartcycling.backend.service.user.account.RegisterService;
import com.smartcycling.backend.service.user.cycle.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveController {
    @Autowired
    private RemoveService removeService;

    @PostMapping("/user/cycle/remove/")
    public Map<String,String> remove(@RequestParam Map<String,String> data)
    {
        return removeService.remove(data);
    }
}
