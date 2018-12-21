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
import com.itdj.admin.model.dto.MenuTree;
import com.itdj.admin.model.entity.SysMenu;
import com.itdj.admin.service.SysMenuService;
import com.itdj.admin.utils.LayuiTreeReplay;
import com.itdj.admin.utils.TreeUtil;
import com.itdj.common.constant.CommonConstant;
import com.itdj.common.util.R;
import com.itdj.common.vo.MenuVO;
import com.xiaoleilu.hutool.collection.CollUtil;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author djj
 * @date 2017/10/31
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    private String prefix = "views/menu/";

    @Autowired
    private SysMenuService sysMenuService;


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
     * 通过角色名称查询用户菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    @GetMapping("/findMenuByRole/{role}")
    public List<MenuVO> findMenuByRole(@PathVariable String role) {
        return sysMenuService.findMenuByRoleName(role);
    }


    /**
     * 返回部门列表
     *
     * @return 部门
     */
    @RequestMapping(value = "/listPage")
    @ResponseBody
    public LayuiTreeReplay getList() {
        SysMenu condition = new SysMenu();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        List<SysMenu> list = sysMenuService.list(new QueryWrapper<>(condition));
        return new LayuiTreeReplay(0, "获得数据成功", list.size(), list, true, "获得数据成功");
    }


//    /**
//     * 返回当前用户的树形菜单集合
//     *
//     * @return 当前用户的树形菜单
//     */
//    @GetMapping(value = "/userMenu")
//    public List<MenuTree> userMenu() {
//        // 获取符合条件得菜单
//        Set<MenuVO> all = new HashSet<>();
//        getRole().forEach(roleName -> all.addAll(sysMenuService.findMenuByRoleName(roleName)));
//        List<MenuTree> menuTreeList = new ArrayList<>();
//        all.forEach(menuVo -> {
//            if (CommonConstant.MENU.equals(menuVo.getType())) {
//                menuTreeList.add(new MenuTree(menuVo));
//            }
//        });
//        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
//        return TreeUtil.bulid(menuTreeList, -1);
//    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @RequestMapping(value = "/tree")

    public String tree() {
        return prefix + "tree";
    }


    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @RequestMapping(value = "/allTree")
    @ResponseBody
    public List<MenuTree> getTree() {
        SysMenu condition = new SysMenu();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        return TreeUtil.bulidTree(sysMenuService.list(new QueryWrapper<>(condition)), -1);
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleName 角色名称
     * @return 属性集合
     */
    @GetMapping("/roleTree/{roleName}")
    public List<Integer> roleTree(@PathVariable String roleName) {
        List<MenuVO> menus = sysMenuService.findMenuByRoleName(roleName);
        List<Integer> menuList = new ArrayList<>();
        for (MenuVO menuVo : menus) {
            menuList.add(menuVo.getMenuId());
        }
        return menuList;
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public SysMenu menu(@PathVariable Integer id) {
        return sysMenuService.getById(id);
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return success/false
     */
    @RequestMapping(value = "/addForm")
    @ResponseBody
    public R<Boolean> addForm(SysMenu sysMenu) {
        try {
            sysMenuService.save(sysMenu);
            return new R<>();
        } catch (Exception e) {
            return new R(e);
        }

    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     * TODO  级联删除下级节点
     */
    @RequestMapping(value = "/remove/{id}")
    @ResponseBody
    public R<Boolean> menuDel(@PathVariable Integer id) {

        try {
            return new R<>(sysMenuService.deleteMenu(id));
        } catch (Exception e) {

            return new R(e);
        }

    }


    /**
     * 编辑菜单
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/editForm")
    @ResponseBody
    public R<Boolean> editForm(SysMenu sysMenu) {

        try {
            sysMenuService.updateMenuById(sysMenu);
            return new R<>();
        } catch (Exception e) {

            return new R(e);
        }
    }

}
