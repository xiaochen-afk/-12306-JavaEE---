package com.itheima.demo12306.Service;

import com.itheima.demo12306.Entity.User;
import com.itheima.demo12306.Mapper.AuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthMapper authMapper;
    public User login(String username, String password) {
        log.info("验证用户登录: {}", username);
        User user = authMapper.login(username,password);
        if (user == null){
            return null;
        }
        return user;
    }

    public int register(String username, String password, String email, String telephone) {
        log.info("注册用户: {}", username);
        User user = authMapper.check_telephone_unique(username,telephone);
        if (user == null){
            authMapper.register(username,password,email,telephone);
            log.info("注册成功");
            return 1;
        }
        return -1;
    }
}
