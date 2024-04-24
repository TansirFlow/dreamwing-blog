package com.dreamwing.service;

import com.dreamwing.pojo.User;

public interface UserService {
    void register(User user);

    User login(User user);
}
