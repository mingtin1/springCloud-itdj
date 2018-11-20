

package com.itdj.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itdj.admin.model.dto.DeptDTO;
import com.itdj.admin.model.dto.DeptTree;
import com.itdj.admin.model.entity.SysDept;
import com.itdj.admin.service.SysDeptService;
import com.itdj.admin.utils.LayuiTreeReplay;
import com.itdj.common.constant.CommonConstant;
import com.itdj.common.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author djj
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
    public String add(@RequestParam("parentId") Integer parentId, Model model) {
        SysDept parentDept = new SysDept();
        if (parentId > 0) {
            parentDept = sysDeptService.getById(parentId);
            model.addAttribute("parentId", parentDept.getDeptId());
            model.addAttribute("parentName", parentDept.getName());
        }
        return prefix + "add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        SysDept sysDept = sysDeptService.getById(id);
        model.addAttribute("sysDept", sysDept);
        Integer parentId = sysDept.getParentId();
        if (parentId > 0) {
            SysDept parentDept = sysDeptService.getById(parentId);
            model.addAttribute("parentName", parentDept.getName());
        }
        return prefix + "edit";
    }

    /**
     * 返回树形菜单
     *
     * @return 树形菜单
     */
    @RequestMapping(value = "/deptTree")
    public String tree() {
        return prefix + "tree";
    }

    /**
     * 返回部门列表
     *
     * @return 部门
     */
    @RequestMapping(value = "/listPage")
    @ResponseBody
    public LayuiTreeReplay getList() {
        SysDept condition = new SysDept();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        List<SysDept> list = sysDeptService.list(new QueryWrapper<>(condition));
        System.out.println(list);
        return new LayuiTreeReplay(0, "获得数据成功", list.size(), list, true, "获得数据成功");
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
     * @param parentId 父级 ID
     * @param name 部门名称
     * @param orderNum 排序
     * @return success/false
     */
    /**
     * @return
     */
    @RequestMapping("/addForm")
    @ResponseBody
    public R<Boolean> addForm(DeptDTO deptDTO) {

        try {
            SysDept sysDept = new SysDept();
            BeanUtils.copyProperties(deptDTO, sysDept);
            sysDept.setParentId(deptDTO.getParentId() != null ? deptDTO.getParentId() : 0);
            sysDeptService.insertDept(sysDept);
            return new R();
        } catch (Exception e) {
            return new R(e);
        }

    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @RequestMapping("/remove/{id}")
    @ResponseBody
    public R<Boolean> remove(@PathVariable Integer id) {

        try {
            return new R(sysDeptService.deleteDeptById(id), "删除成功");
        } catch (Exception e) {
            return new R(e);
        }
    }

    /**
     * 编辑
     *
     * @param deptDTO 实体
     * @return success/false
     */
    @RequestMapping("/edptForm")
    @ResponseBody
    public R<Boolean> edptForm(DeptDTO deptDTO) {
        try {
            SysDept sysDept = new SysDept();
            BeanUtils.copyProperties(deptDTO, sysDept);
            sysDept.setUpdateTime(new Date());
            sysDeptService.updateDeptById(sysDept);
            return new R();
        } catch (Exception e) {
            return new R(e);
        }

    }
}
