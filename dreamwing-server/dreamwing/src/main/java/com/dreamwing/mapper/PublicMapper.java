package com.dreamwing.mapper;

import com.dreamwing.pojo.CategoryVO;
import com.dreamwing.pojo.TagVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PublicMapper {
    @Select("select article.id as id,user_id,username as author,category_id,category_name,article_cover,article_title,article_abstract," +
            "article_content,type,look_num,article.password as password,origin_url,article.create_time as create_time," +
            "article.update_time as update_time " +
            "from article join user join category on user_id=user.id and category_id=category.id " +
            "where is_delete=0 and status=1 and (article.password is null or article.password='')")
    Object getArticleList();
    @Select("select tag.id as id,name,tag.create_time as create_time," +
            "tag.update_time as update_time,tag.create_user as create_user," +
            "user.username as create_user_name " +
            "from tag join user on create_user=user.id join article_tag on tag.id = article_tag.tag_id " +
            "where article_tag.article_id=#{articleId}")
    List<TagVO> getTagListByArticleId(Integer articleId);

    @Select("select category.id as id,category_name,category.create_time as create_time," +
            "category.update_time as update_time,create_user,username as create_user_name " +
            "from category join user on create_user=user.id")
    List<CategoryVO> getCategoryList();
}
