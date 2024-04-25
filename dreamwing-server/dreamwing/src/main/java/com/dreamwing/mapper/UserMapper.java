package com.dreamwing.mapper;

import com.dreamwing.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    @Insert("insert into user(username, password, email, create_time, update_time,role,state) " +
            "values (#{username},#{password},#{email},NOW(),NOW(),#{role},#{state})")
    void register(User user);

    @Select("select * from user where username=#{username} and password=#{password}")
    User login(User user);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=NOW(),signature=#{signature} where id=#{id}")
    void update(User user);

    @Select("select COUNT(username) from user;")
    Integer getUserNum();

    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    @Update("update user set password=#{newPwd},update_time=NOW() where id=#{id}")
    void updatePwd(String newPwd, Integer id);

    @Update("update user set avatar=#{avatarUrl},update_time=NOW() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Select("select * from user where id=#{id}")
    User findById(Integer id);
}
