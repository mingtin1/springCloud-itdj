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

package com.itdj.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itdj.admin.model.dto.RoleDTO;
import com.itdj.admin.model.entity.SysRole;
import com.itdj.admin.utils.LayuiReplay;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author djj
 * @since 2017-10-29
 */
public interface SysRoleService extends IService<SysRole> {



    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    Boolean insertRole(RoleDTO roleDto);

//    /**
//     * 分页查角色列表
//     *
//     * @param objectQuery         查询条件
//     * @param objectEntityWrapper wapper
//     * @return page
//     */
//    Page selectwithDeptPage(Query<Object> objectQuery, EntityWrapper<Object> objectEntityWrapper);

    /**
     * 更新角色
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    Boolean updateRoleById(RoleDTO roleDto);

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    List<SysRole> selectListByDeptId(Integer deptId);

    /**
     * 分页查角色列表
     * @return
     */
    LayuiReplay listPage();

}