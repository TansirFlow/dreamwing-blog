package com.dreamwing.mapper;

import com.dreamwing.pojo.Attachment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttachmentMapper {
    @Insert("insert into attachment(type, url, create_time) VALUES (#{type},#{url},NOW())")
    void add(String type, String url);

    @Select("select * from attachment where id=#{id}")
    Attachment getById(Integer id);

    @Delete("delete from attachment where id=#{id}")
    void delete(Integer id);

    @Select("select * from attachment;")
    List<Attachment> getAll();
}
