package com.dreamwing.service;

import com.dreamwing.pojo.User;

public interface UserService {
    void register(User user);

    void update(User user);

    User findByUserName(String username);

    void updatePassword(String newPwd, Integer id);

    void updateAvatar(String avatarUrl);

    User getUserInfo();
}
