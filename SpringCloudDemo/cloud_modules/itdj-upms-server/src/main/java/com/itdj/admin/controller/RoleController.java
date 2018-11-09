/*
 *    Copyright (c) 2018-2025, djj All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: djj (wangiegie@gmail.com)
 */

package com.itdj.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itdj.admin.model.entity.SysRole;
import com.itdj.admin.service.SysRoleService;
import com.itdj.admin.utils.LayuiReplay;
import com.itdj.admin.utils.LayuiTreeReplay;
import com.itdj.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author djj
 * @date 2017/11/5
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    private String prefix = "views/role/";

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/list")
    public String list() {
        return prefix + "list";
    }

    @RequestMapping("/add")
    public String add() {
        return prefix + "add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

        return prefix + "edit";
    }

    /**
     * 返回部门列表
     *
     * @return 部门
     */
    @RequestMapping(value = "/listPage")
    @ResponseBody
    public LayuiReplay getList() {
        return sysRoleService.listPage();
    }
//    @Autowired
//    private SysRoleMenuService sysRoleMenuService;

//    /**
//     * 通过ID查询角色信息
//     *
//     * @param id ID
//     * @return 角色信息
//     */
//    @GetMapping("/{id}")
//    public SysRole role(@PathVariable Integer id) {
//        return sysRoleService.getById(id);
//    }
//
//    /**
//     * 添加角色
//     *
//     * @param roleDto 角色信息
//     * @return success、false
//     */
//    @PostMapping
//    public R<Boolean> role(@RequestBody RoleDTO roleDto) {
//        return new R<>(sysRoleService.insertRole(roleDto));
//    }
//
//    /**
//     * 修改角色
//     *
//     * @param roleDto 角色信息
//     * @return success/false
//     */
//    @PutMapping
//    public R<Boolean> roleUpdate(@RequestBody RoleDTO roleDto) {
//        return new R<>(sysRoleService.updateRoleById(roleDto));
//    }
//
//    /**
//     * 删除
//     *
//     * @param id
//     * @return
//     */
//
//    @DeleteMapping("/{id}")
//    public R<Boolean> roleDel(@PathVariable Integer id) {
//        SysRole sysRole = sysRoleService.selectById(id);
//        sysRole.setDelFlag(CommonConstant.STATUS_DEL);
//        return new R<>(sysRoleService.updateById(sysRole));
//    }
//
//    /**
//     * 获取角色列表
//     *
//     * @param deptId 部门ID
//     * @return 角色列表
//     */
//    @GetMapping("/roleList/{deptId}")
//    public List<SysRole> roleList(@PathVariable Integer deptId) {
//        return sysRoleService.selectListByDeptId(deptId);
//
//    }
//
//    /**
//     * 分页查询角色信息
//     *
//     * @param params 分页对象
//     * @return 分页对象
//     */
//    @RequestMapping("/rolePage")
//    public Page rolePage(@RequestParam Map<String, Object> params) {
//        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
//        return sysRoleService.selectwithDeptPage(new Query<>(params), new EntityWrapper<>());
//    }

//    /**
//     * 更新角色菜单
//     *
//     * @param roleId  角色ID
//     * @param menuIds 菜单结合
//     * @return success、false
//     */
//    @PutMapping("/roleMenuUpd")
//    public R<Boolean> roleMenuUpd(Integer roleId, @RequestParam(value = "menuIds", required = false) String menuIds) {
//        SysRole sysRole = sysRoleService.selectById(roleId);
//        return new R<>(sysRoleMenuService.insertRoleMenus(sysRole.getRoleCode(), roleId, menuIds));
//    }
}
