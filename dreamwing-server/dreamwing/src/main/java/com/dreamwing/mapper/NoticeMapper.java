package com.dreamwing.mapper;

import com.dreamwing.pojo.NoticeDTO;
import com.dreamwing.pojo.NoticeVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Insert("insert into notice(title, content, create_time, create_user) " +
            "VALUES (#{title},#{content},#{createTime},#{createUser})")
    void add(NoticeDTO noticeDTO);

    @Update("update notice set title=#{title},content=#{content},update_time=#{updateTime} where id=#{id}")
    void update(NoticeDTO noticeDTO);

    @Select("select notice.id as id,title,content,notice.create_time as create_time," +
            "notice.update_time as update_time,notice.create_user as create_user," +
            "user.username as create_user_name " +
            "from notice join user on notice.create_user = user.id " +
            "where notice.id=#{id}")
    NoticeVO getById(Integer id);

    @Select("select notice.id as id,title,content,notice.create_time as create_time," +
            "notice.update_time as update_time,notice.create_user as create_user," +
            "user.username as create_user_name " +
            "from notice join user on notice.create_user = user.id")
    List<NoticeVO> getAll();
}
