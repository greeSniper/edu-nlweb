package com.tangzhe.cslg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于跳转页面
 */
@Controller
@RequestMapping("page")
public class PageController {

    //前台页面
    private static final String PRE = "common/pre";

    @RequestMapping("/{common}/{index}.action") //page/admin/userlist.action
    public String page(@PathVariable String common, @PathVariable String index) {
        //WEB-INF/pages/common/index.jsp
        return common + "/" + index;
    }

    /**
     * 进入前台页面
     */
    @RequestMapping("/topre")
    public String topre() {
        return PRE;
    }

}
