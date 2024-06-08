package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.RoleMapper;
import com.dreamwing.pojo.AuthorityVO;
import com.dreamwing.pojo.RoleVO;
import com.dreamwing.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public void add(String name) {
        roleMapper.add(name);
    }

    @Override
    public void addAuthorityByRoleId(Integer roleId, Integer authorityId) {
        roleMapper.addAuthorityByRoleId(roleId,authorityId);
    }

    @Override
    public List<AuthorityVO> getAuthorityByRoleId(Integer roleId) {
        List<AuthorityVO> authorityVOList=roleMapper.getAuthorityByRoleId(roleId);
        return authorityVOList;
    }

    @Override
    public void deleteRoleAuthorityById(Integer id) {
        roleMapper.deleteRoleAuthorityById(id);
    }

    @Override
    public void deleteRoleById(Integer id) {
        List<AuthorityVO> authorityVOList=getAuthorityByRoleId(id);
        if(!authorityVOList.isEmpty()){
            throw new DreamWingRuntimeException("改角色所具有的权限未清空，禁止删除");
        }
        roleMapper.deleteRoleById(id);
    }

    @Override
    public void updateRoleName(Integer id, String name) {
        roleMapper.updateRoleName(id,name);
    }

    @Override
    public List<RoleVO> getRoleList() {
        List<RoleVO> roleVOList=roleMapper.getRoleList();
        return roleVOList;
    }

    @Override
    public List<AuthorityVO> getAuthorityList() {
        List<AuthorityVO> authorityVOList=roleMapper.getAuthorityList();
        return authorityVOList;
    }

    public RoleVO getRoleByUserId(Integer userId) {
        RoleVO roleVO=roleMapper.getRoleById(userId);
        return roleVO;
    }
}

