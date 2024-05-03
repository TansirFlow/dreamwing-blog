package com.dreamwing.mapper;

import com.dreamwing.pojo.TagVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TagMapper {
    @Insert("insert into tag(name, create_time,create_user) values (#{name},NOW(),#{id})")
    void add(String name,Integer id);

    @Select("select tag.id as id,name,tag.create_time as create_time," +
            "tag.update_time as update_time,tag.create_user as create_user," +
            "user.username as create_user_name " +
            "from tag join user on create_user=user.id " +
            "where tag.id=#{id}")
    TagVO getById(Integer id);

    @Select("select tag.id as id,name,tag.create_time as create_time," +
            "tag.update_time as update_time,tag.create_user as create_user," +
            "user.username as create_user_name " +
            "from tag join user on create_user=user.id")
    List<TagVO> getAll();


    void tryToAddTagList(Integer userId,ArrayList<String> tagList);

    List<TagVO> getTagVOListByTagNameList(List<String> tagNameList);
}
