package com.dreamwing.mapper;

import com.dreamwing.pojo.RegisterVerifyCode;
import jakarta.validation.constraints.Email;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface RegisterVerifyCodeMapper {
    @Insert("insert into register_verify_code(username, email, code,expiration_time) " +
            "VALUES (#{username},#{email},#{verifyCode},NOW() + INTERVAL 5 MINUTE)")
    void add(String username, String email, String verifyCode);

    @Select("select * from register_verify_code " +
            "where username=#{username} and email=#{email}")
    RegisterVerifyCode find(String username, String email);

    @Update("update register_verify_code " +
            "set code=#{verifyCode},expiration_time=NOW() + INTERVAL 5 MINUTE " +
            "where username=#{username} and email=#{email}")
    void update(String username, String email, String verifyCode);

    void findVerifyCode(String username, String email, String verifyCode);

    @Delete("delete from register_verify_code where username=#{username} and email=#{email}")
    void delete(String username, String email);
}
