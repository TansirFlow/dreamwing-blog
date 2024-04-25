package com.dreamwing.service.impl;

import com.dreamwing.mapper.UserMapper;
import com.dreamwing.pojo.User;
import com.dreamwing.service.UserService;
import com.dreamwing.utils.Md5Util;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        Integer nums = userMapper.getUserNum();
        if (nums == 0) {
            user.setRole(1);
        } else {
            user.setRole(3);
        }
        String md5Password = Md5Util.getMD5String(user.getInputPassword());
        user.setPassword(md5Password);
        user.setState("正常");
        userMapper.register(user);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void updatePassword(String newPwd, Integer id) {
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public User getUserInfo() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        return userMapper.findByUserName(username);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}
