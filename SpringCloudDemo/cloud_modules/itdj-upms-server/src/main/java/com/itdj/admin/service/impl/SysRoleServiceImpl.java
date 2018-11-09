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

package com.itdj.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itdj.admin.mapper.SysRoleDeptMapper;
import com.itdj.admin.mapper.SysRoleMapper;
import com.itdj.admin.model.dto.RoleDTO;
import com.itdj.admin.model.entity.SysRole;
import com.itdj.admin.model.entity.SysRoleDept;
import com.itdj.admin.service.SysRoleService;
import com.itdj.admin.utils.LayuiReplay;
import com.itdj.common.constant.CommonConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author djj
 * @since 2017-10-29
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    @Override
    public Boolean insertRole(RoleDTO roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.insert(sysRole);
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setRoleId(sysRole.getRoleId());
        roleDept.setDeptId(roleDto.getRoleDeptId());
        sysRoleDeptMapper.insert(roleDept);
        return true;
    }

    @Override
    public LayuiReplay listPage() {
        SysRole sysRole = new SysRole();
        sysRole.setDelFlag(CommonConstant.STATUS_NORMAL);
        List<RoleDTO> roleDTOList = sysRoleMapper.selectRolePage();
        return new LayuiReplay(0, "获得数据成功", roleDTOList.size(), roleDTOList);
    }
    /*

     */
/**
 * 分页查角色列表
 *
 * @param query   查询条件
 * @param wrapper wapper
 * @return page
 *//*

    @Override
    public Page selectwithDeptPage(Query<Object> query, EntityWrapper<Object> wrapper) {
        query.setRecords(sysRoleMapper.selectRolePage(query, query.getCondition()));
        return query;
    }
*/

    /**
     * 更新角色
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateRoleById(RoleDTO roleDto) {
        //删除原有的角色部门关系
        SysRoleDept condition = new SysRoleDept();
        condition.setRoleId(roleDto.getRoleId());
//        sysRoleDeptMapper.delete(new EntityWrapper<>(condition));

        //更新角色信息
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.updateById(sysRole);

        //维护角色部门关系
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setRoleId(sysRole.getRoleId());
        roleDept.setDeptId(roleDto.getRoleDeptId());
        sysRoleDeptMapper.insert(roleDept);
        return true;
    }

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectListByDeptId(Integer deptId) {
        return sysRoleMapper.selectListByDeptId(deptId);
    }
}
