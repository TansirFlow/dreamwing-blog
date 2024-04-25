package com.dreamwing.mapper;

import com.dreamwing.pojo.OtherVerifyCode;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OtherVerifyCodeMapper {

    @Select("select * from other_verify_code where username=#{username} and type=#{type}")
    OtherVerifyCode find(String username, String type);

    @Insert("insert into other_verify_code (username, type, code, expiration_time) " +
            "VALUES (#{username},#{type},#{verifyCode},NOW() + INTERVAL 5 MINUTE)")
    void add(String username, String type, String verifyCode);

    @Update("update other_verify_code set code=#{verifyCode},expiration_time=NOW() + INTERVAL 5 MINUTE " +
            "where username=#{username} and type=#{type}")
    void update(String username, String type, String verifyCode);

    @Delete("delete from other_verify_code where username=#{username} and type=#{type}")
    void delete(String username, String type);
}
