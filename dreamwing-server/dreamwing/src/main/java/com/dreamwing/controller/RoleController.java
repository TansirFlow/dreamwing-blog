package com.dreamwing.controller;

import com.dreamwing.pojo.AuthorityVO;
import com.dreamwing.pojo.Result;
import com.dreamwing.pojo.RoleVO;
import com.dreamwing.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Validated
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 增加角色
     *
     * @param name
     * @return
     */
    @PostMapping("/add/{name}")
    public Result add(@PathVariable String name) {
        roleService.add(name);
        return Result.success();
    }

    /**
     * 删除某个角色
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        roleService.deleteRoleById(id);
        return Result.success();
    }

    /**
     * 获取所有角色列表
     *
     * @return
     */
    @GetMapping("/roleList")
    public Result<List<RoleVO>> getRoleList() {
        List<RoleVO> roleVOList = roleService.getRoleList();
        return Result.success(roleVOList);
    }

    /**
     * 修改角色名
     *
     * @param name
     * @return
     */
    @PostMapping("/updateRoleName")
    public Result updateRoleName(Integer roleId, String name) {
        roleService.updateRoleName(roleId, name);
        return Result.success();
    }

    /**
     * 给角色增加权限
     *
     * @param roleId
     * @param authorityId
     * @return
     */
    @PostMapping("/addAuthorityByRoleId")
    public Result addAuthorityByRoleId(Integer roleId, Integer authorityId) {
        roleService.addAuthorityByRoleId(roleId, authorityId);
        return Result.success();
    }

    /**
     * 根据角色id查看角色具有的权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/getAuthorityByRoleId/{roleId}")
    public Result<List<AuthorityVO>> getAuthorityByRoleId(@PathVariable Integer roleId) {
        List<AuthorityVO> authorityVOList = roleService.getAuthorityByRoleId(roleId);
        return Result.success(authorityVOList);
    }

    /**
     * 删除某个角色和权限的绑定
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteRoleAuthorityById/{id}")
    public Result deleteRoleAuthorityById(@PathVariable Integer id) {
        roleService.deleteRoleAuthorityById(id);
        return Result.success();
    }

    @GetMapping("/authorityList")
    public Result<List<AuthorityVO>> getAuthorityList() {
        List<AuthorityVO> authorityVOList = roleService.getAuthorityList();
        return Result.success(authorityVOList);
    }


}
