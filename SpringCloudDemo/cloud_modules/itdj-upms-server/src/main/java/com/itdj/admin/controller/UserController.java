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

import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private String prefix = "views/sys/user/";
    @Autowired
    private SysUserService userService;

    @RequestMapping("/index")
    public String index(){
        return "views/index";
    }

    @RequestMapping("/login")
    public String login(){
        return "views/login";
    }
    @RequestMapping("/get/{id}")
    @ResponseBody
    public SysUser getUser(@PathVariable("id") Integer id) {
        SysUser sysUser = userService.selectById(id);
        return sysUser;
    }

    @RequestMapping("/list")
    public String user() {
        System.out.println(prefix + "user");
        return prefix + "user";
    }
}
