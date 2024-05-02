package com.dreamwing.mapper;

import com.dreamwing.pojo.CategoryDTO;
import com.dreamwing.pojo.CategoryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into category(category_name, create_time,create_user) VALUES (#{categoryName},#{createTime},#{createUser})")
    void add(CategoryDTO categoryDTO);

    @Select("select category.id as id,category_name,category.create_time as create_time," +
            "category.update_time as update_time,create_user,username as create_user_name " +
            "from category join user on create_user=user.id " +
            "where category.id=#{id}")
    CategoryVO getById(Integer id);

    @Select("select category.id as id,category_name,category.create_time as create_time," +
            "category.update_time as update_time,create_user,username as create_user_name " +
            "from category join user on create_user=user.id")
    List<CategoryVO> getAll();

    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
}
