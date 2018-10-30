package com.itdj.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller

public class LoginController {

    @RequestMapping(value = {"/", "index"})
    public String index() {
        return "views/index";
    }


    @RequestMapping("/logut")
    public String logut() {
        return "views/login";
    }


    @RequestMapping("/data-table")
    public String dataTable() {
        return "views/demo/data-table";
    }


    @RequestMapping("/login")
    public String login() {
        return "views/login";
    }

    @RequestMapping("/test")
    public String test() {
        return "views/test";
    }
}
