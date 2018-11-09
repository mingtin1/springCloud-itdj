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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itdj.admin.mapper.SysDeptMapper;
import com.itdj.admin.mapper.SysDeptRelationMapper;
import com.itdj.admin.model.dto.DeptDTO;
import com.itdj.admin.model.dto.DeptTree;
import com.itdj.admin.model.entity.SysDept;
import com.itdj.admin.model.entity.SysDeptRelation;
import com.itdj.admin.service.SysDeptService;
import com.itdj.admin.utils.TreeUtil;
import com.itdj.common.constant.CommonConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author djj
 * @since 2018-01-20
 */
@Service
@Transactional
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDeptRelationMapper sysDeptRelationMapper;

    /**
     * 添加信息部门
     *
     * @param sysDept 部门
     * @return
     */
    @Override
    public Boolean insertDept(SysDept sysDept) {
        sysDept.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysDept.setCreateTime(new Date());
        this.save(sysDept);
        this.insertDeptRelation(sysDept);
        return Boolean.TRUE;
    }

    /**
     * 维护部门关系
     *
     * @param sysDept 部门
     */
    private void insertDeptRelation(SysDept sysDept) {
        //增加部门关系表
        QueryWrapper<SysDeptRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("descendant", sysDept.getParentId());
        List<SysDeptRelation> deptRelationList = sysDeptRelationMapper.selectList(queryWrapper);
        for (SysDeptRelation sysDeptRelation : deptRelationList) {
            sysDeptRelation.setDescendant(sysDept.getDeptId());
            sysDeptRelationMapper.insert(sysDeptRelation);
        }
        //自己也要维护到关系表中
        SysDeptRelation own = new SysDeptRelation();
        own.setDescendant(sysDept.getDeptId());
        own.setAncestor(sysDept.getDeptId());
        sysDeptRelationMapper.insert(own);
    }

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    @Override
    public Boolean deleteDeptById(Integer id) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(id);
        sysDept.setUpdateTime(new Date());
        sysDept.setDelFlag(CommonConstant.STATUS_DEL);
        this.removeById(sysDept);
        sysDeptMapper.deleteDeptRealtion(id);
        return Boolean.TRUE;
    }

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    @Override
    public Boolean updateDeptById(SysDept sysDept) {
        //更新部门状态
        this.updateById(sysDept);
        //删除部门关系
        sysDeptMapper.deleteDeptRealtion(sysDept.getDeptId());
        //新建部门关系
        this.insertDeptRelation(sysDept);
        return Boolean.TRUE;
    }
//

    /**
     * 查询部门树
     *
     * @param sysDeptEntityWrapper
     * @return 树
     */
    @Override
    public List<DeptTree> selectListTree(QueryWrapper<SysDept> sysDeptEntityWrapper) {
        return getDeptTree(this.list(sysDeptEntityWrapper), 0);
    }

    /**
     * 构建部门树
     *
     * @param depts 部门
     * @param root  根节点
     * @return
     */
    private List<DeptTree> getDeptTree(List<SysDept> depts, int root) {
        List<DeptTree> trees = new ArrayList<>();
        DeptTree node;
        for (SysDept dept : depts) {
            if (dept.getParentId().equals(dept.getDeptId())) {
                continue;
            }
            node = new DeptTree();
            node.setId(dept.getDeptId());
            node.setParentId(dept.getParentId());
            node.setName(dept.getName());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }
}
