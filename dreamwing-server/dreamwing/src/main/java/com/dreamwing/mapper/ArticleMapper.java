package com.dreamwing.mapper;

import com.dreamwing.pojo.Article;
import com.dreamwing.pojo.ArticleVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(user_id, category_id, article_cover, article_title, article_abstract, " +
            "article_content, is_delete, status, type, look_num, password, origin_url, create_time, update_time) " +
            "VALUES (#{userId},#{categoryId},#{articleCover},#{articleTitle},#{articleAbstract},#{articleContent}," +
            "#{isDelete},#{status},#{type},#{lookNum},#{password},#{originUrl},#{createTime},NOW())")
    void add(Article article);

    @Select("select * from article where id=#{id}")
    Article getById(Integer id);

    @Select("select article.id as id,user_id,username as author,category_id,category_name,article_cover,article_title,article_abstract," +
            "article_content,type,look_num,article.password as password,origin_url,article.create_time as create_time," +
            "article.update_time as update_time " +
            "from article join user join category on user_id=user.id and category_id=category.id " +
            "where is_delete=0 and status=1 and (article.password is null or article.password='')")
    List<ArticleVO> getList();
}
