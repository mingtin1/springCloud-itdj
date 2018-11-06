
package com.itdj.admin.controller;

import com.itdj.admin.model.dto.UserDTO;
import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.model.queryPage.UserPage;
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
        LayuiReplay r = userService.userPage(userPage);
        return r;
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
        BeanUtils.copyProperties(userDTO, sysUser);
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDTO.getNewpassword1()));
        boolean save = userService.save(sysUser);
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
        model.addAttribute("uservo", uservo);
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
        SysUser user = userService.getById(userDTO.getUserId());
        boolean b = userService.updateUser(userDTO, user.getUsername());
        if (b) {
            return new R<>();
        }
        return new R<>();
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
        SysUser sysUser = userService.getById(id);
        return new R<>(userService.deleteUserById(sysUser));
    }
}
