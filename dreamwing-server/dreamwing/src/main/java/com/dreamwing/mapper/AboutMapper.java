package com.dreamwing.mapper;

import com.dreamwing.pojo.AboutVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AboutMapper {
    @Insert("insert into about(content, create_time,create_user) values (#{content},NOW(),#{id})")
    void add(String content, Integer id);

    @Select("select about.id as id,content,about.create_time as create_time," +
            "about.update_time as update_time,about.create_user as create_user," +
            "user.username as create_user_name " +
            "from about join user on create_user=user.id " +
            "where about.id=#{id}")
    AboutVO getById(Integer id);

    @Select("select about.id as id,content,about.create_time as create_time," +
            "about.update_time as update_time,about.create_user as create_user," +
            "user.username as create_user_name " +
            "from about join user on create_user=user.id")
    List<AboutVO> getAll();

    @Delete("delete from about where id=#{id}")
    void delete(Integer id);
}
