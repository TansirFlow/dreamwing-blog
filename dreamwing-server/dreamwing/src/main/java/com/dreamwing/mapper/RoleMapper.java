package com.dreamwing.mapper;

import com.dreamwing.pojo.AuthorityVO;
import com.dreamwing.pojo.RoleVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Insert("insert into role(name, is_disable, create_time) VALUES (#{name},0,NOW())")
    void add(String name);


    @Insert("insert into role_authority(role_id, authority_id) VALUES (#{roleId},#{authorityId})")
    void addAuthorityByRoleId(Integer roleId, Integer authorityId);

    @Select("select authority.id as id,authority.name as name," +
            "authority.create_time as create_time,authority.update_time as update_time " +
            "from role_authority join authority on role_authority.authority_id = authority.id " +
            "where role_authority.role_id=#{roleId}")
    List<AuthorityVO> getAuthorityByRoleId(Integer roleId);

    @Delete("delete from role_authority where id=#{id}")
    void deleteRoleAuthorityById(Integer id);

    @Delete("delete from role where id=#{id}")
    void deleteRoleById(Integer id);

    @Update("update role set name=#{name} where id=#{id}")
    void updateRoleName(Integer id, String name);

    @Select("select * from role;")
    List<RoleVO> getRoleList();

    @Select("select * from authority")
    List<AuthorityVO> getAuthorityList();
}
