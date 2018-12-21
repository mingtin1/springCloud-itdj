
package com.itdj.admin.controller;

import com.itdj.admin.model.dto.UserDTO;
import com.itdj.admin.model.entity.SysRole;
import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.model.entity.SysUserRole;
import com.itdj.admin.model.queryPage.UserPage;
import com.itdj.admin.service.SysRoleService;
import com.itdj.admin.service.SysUserService;
import com.itdj.admin.utils.LayuiReplay;
import com.itdj.common.constant.CommonConstant;
import com.itdj.common.util.R;
import com.itdj.common.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author djj
 * @since 2018-10-16
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private String prefix = "views/user/";
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/get/{id}")
    @ResponseBody
    public SysUser getUser(@PathVariable("id") Integer id) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(id);
        sysUser = userService.getById(sysUser);
        return sysUser;
    }

    @RequestMapping("/list")
    public String list() {
        return prefix + "list";
    }

    /**
     * 分页查询用户
     *
     * @return 用户集合
     */
    @RequestMapping("/userPage")
    @ResponseBody
    public LayuiReplay userPage(UserPage userPage) {
        return userService.userPage(userPage);
    }

    @RequestMapping("/add")
    public String add() {
        return prefix + "add";
    }

    /**
     * 新增用户
     *
     * @param userDTO
     * @return
     */
    @RequestMapping("/addForm")
    @ResponseBody
    public R<Boolean> addForm(UserDTO userDTO) {
        SysUser sysUser = new SysUser();
        List<Integer> nullArr = new ArrayList<Integer>();
        nullArr.add(null);
        List<Integer> role1 = userDTO.getRole();
        role1.removeAll(nullArr);
        userDTO.setRole(role1);
        BeanUtils.copyProperties(userDTO, sysUser);
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDTO.getNewpassword1()));
        userService.save(sysUser);
        List<Integer> role = userDTO.getRole();
        role.forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return new R();
    }

    /**
     * 编辑用户
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        UserVO uservo = userService.selectUserVoById(id);

        List<SysRole> sysRoles = sysRoleService.selectListByDeptId(uservo.getDeptId());
        model.addAttribute("uservo", uservo);
        model.addAttribute("roles", sysRoles);
        return prefix + "edit";
    }

    /**
     * 提交编辑
     *
     * @param userDTO
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public R<Boolean> update(UserDTO userDTO) {
        List<Integer> nullArr = new ArrayList<Integer>();
        nullArr.add(null);
        List<Integer> role1 = userDTO.getRole();
        role1.removeAll(nullArr);
        userDTO.setRole(role1);
        SysUser user = userService.getById(userDTO.getUserId());
        return new R<>(userService.updateUser(userDTO, user.getUsername()));
    }

    @RequestMapping("/test")
    public String test() {
        return prefix + "test";
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody
    public R<Boolean> remove(Long id) {
        try {
            SysUser sysUser = userService.getById(id);
            return new R<>(userService.deleteUserById(sysUser));
        } catch (Exception e) {
            return new R<>(e);
        }

    }
}
