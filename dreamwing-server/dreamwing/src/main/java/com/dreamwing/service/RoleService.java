package com.dreamwing.service;

import com.dreamwing.pojo.AuthorityVO;
import com.dreamwing.pojo.RoleVO;

import java.util.List;

public interface RoleService {
    void add(String name);

    void addAuthorityByRoleId(Integer roleId, Integer authorityId);

    List<AuthorityVO> getAuthorityByRoleId(Integer roleId);

    void deleteRoleAuthorityById(Integer id);

    void deleteRoleById(Integer id);

    void updateRoleName(Integer id, String name);

    List<RoleVO> getRoleList();

    List<AuthorityVO> getAuthorityList();
}
