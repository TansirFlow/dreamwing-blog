package com.dreamwing.service.impl;

import com.dreamwing.mapper.UserMapper;
import com.dreamwing.pojo.User;
import com.dreamwing.service.UserService;
import com.dreamwing.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void register(User user) {
        String md5Password = Md5Util.getMD5String(user.getInputPassword());
        user.setPassword(md5Password);
        userMapper.register(user);
    }

    @Override
    public User login(User user) {
        String password = Md5Util.getMD5String(user.getInputPassword());
        user.setPassword(password);
        System.out.println(user);
        return userMapper.login(user);
    }
}
