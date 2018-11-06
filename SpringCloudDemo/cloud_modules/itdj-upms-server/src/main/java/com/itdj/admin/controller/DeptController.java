/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
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
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.itdj.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itdj.admin.model.dto.DeptTree;
import com.itdj.admin.model.entity.SysDept;
import com.itdj.admin.service.SysDeptService;
import com.itdj.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2018-01-20
 */
@Controller
@RequestMapping("/dept")
public class DeptController {
    private String prefix = "views/dept/";
    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("/list")
    public String list() {
        return prefix + "list";
    }

    @RequestMapping("/add")
    public String add() {
        return prefix + "add";
    }

    @RequestMapping("/edit")
    public String edit() {
        return prefix + "edit";
    }


    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @GetMapping("/{id}")
    public SysDept get(@PathVariable Integer id) {
        return sysDeptService.getById(id);
    }


    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<DeptTree> getTree() {
        SysDept condition = new SysDept();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        return sysDeptService.selectListTree(new QueryWrapper<>(condition));
    }

    /**
     * 添加
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PostMapping
    public Boolean add(@RequestBody SysDept sysDept) {
        return sysDeptService.insertDept(sysDept);
    }

//    /**
//     * 删除
//     *
//     * @param id ID
//     * @return success/false
//     */
//    @DeleteMapping("/{id}")
//    public Boolean delete(@PathVariable Integer id) {
//        return sysDeptService.deleteDeptById(id);
//    }

//    /**
//     * 编辑
//     *
//     * @param sysDept 实体
//     * @return success/false
//     */
//    @PutMapping
//    public Boolean edit(@RequestBody SysDept sysDept) {
//        sysDept.setUpdateTime(new Date());
//        return sysDeptService.updateDeptById(sysDept);
//    }
}
