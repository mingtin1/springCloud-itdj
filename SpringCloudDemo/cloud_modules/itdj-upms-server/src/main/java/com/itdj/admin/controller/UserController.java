
package com.itdj.admin.controller;

import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.model.queryPage.UserPage;
import com.itdj.admin.service.SysUserService;
import com.itdj.admin.utils.LayuiReplay;
import com.itdj.common.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author djj
 * @since 2018-10-16
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private String prefix = "views/user/";
    @Autowired
    private SysUserService userService;

    @RequestMapping("/get/{id}")
    @ResponseBody
    public SysUser getUser(@PathVariable("id") Integer id) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(id);
        sysUser = userService.getById(sysUser);
        return sysUser;
    }

    @RequestMapping("/user")
    public String user() {
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

        List<UserVO> list = userService.selectWithRolePage(userPage);
        LayuiReplay r = new LayuiReplay(0, "获得数据成功", list.size(), list);
        return r;
    }
    @RequestMapping("/add")
    public String add()
    {
        return prefix + "add";
    }
    @RequestMapping("/update")
    public String update()
    {
        return prefix + "update";
    }

}
