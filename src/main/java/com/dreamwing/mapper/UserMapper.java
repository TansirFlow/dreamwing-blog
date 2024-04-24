package com.dreamwing.mapper;

import com.dreamwing.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    @Insert("insert into user(username, password, email, create_time, update_time) " +
            "values (#{username},#{password},#{email},NOW(),NOW())")
    void register(User user);

    @Select("select * from user where username=#{username} and password=#{password}")
    User login(User user);
}
