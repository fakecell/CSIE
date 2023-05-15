package com.smartcycling.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartcycling.backend.mapper.UserMapper;
import com.smartcycling.backend.pojo.User;
import com.smartcycling.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String passoword, String confirmedPassword) {
        Map<String,String> map = new HashMap<>();

        if(username == null) {
            map.put("error_message","用户名不能为空");
            return map;
        }
        if(confirmedPassword == null || passoword == null){
            map.put("error_message","密码不能为空");
            return map;
        }
        username = username.trim();
        if(username.length() == 0){
            map.put("error_message","用户名不能为空");
            return map;
        }
        if(passoword.length() == 0 || confirmedPassword.length() == 0){
            map.put("error_message","密码不能为空");
            return map;
        }

        if(username.length() > 100){
            map.put("error_message","用户名长度不能大于100个字符");
            return map;
        }
        if(passoword.length() > 100 || confirmedPassword.length() > 100){
            map.put("error_message","密码长度不能大于100个字符");
            return map;
        }

        if(!passoword.equals(confirmedPassword)){
            map.put("error_message","两次输入的密码不一致");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(queryWrapper);

        if(!users.isEmpty()){
            map.put("error_message","用户名已存在");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(passoword);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/138581_lg_95f527899a.png";

        User user = new User(null,username,encodedPassword,photo,new Date(),new Date());
        userMapper.insert(user);
        map.put("error_message","success");

        return map;

    }
}