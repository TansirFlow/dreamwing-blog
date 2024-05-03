package com.dreamwing.mapper;

import com.dreamwing.pojo.CommentDTO;
import com.dreamwing.pojo.CommentVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(user_id, content, parent_comment_id, type, parent_id, is_delete, is_review, create_time) " +
            "VALUES (#{userId},#{content},#{parentCommentId},#{type},#{parentId},#{idDelete},#{idReview},#{createTime})")
    void add(CommentDTO commentDTO);

    @Select("select comment.id as id,user_id,content,parent_comment_id,type," +
            "parent_id,is_delete,is_review,user.create_time,user.update_time " +
            "from comment join user on comment.user_id = user.id " +
            "where comment.id=#{id}")
    CommentVO getById(Integer id);

    @Select("select comment.id as id,user_id,content,parent_comment_id,type," +
            "parent_id,is_delete,is_review,user.create_time,user.update_time " +
            "from comment join user on comment.user_id = user.id")
    List<CommentVO> getAll();
}
