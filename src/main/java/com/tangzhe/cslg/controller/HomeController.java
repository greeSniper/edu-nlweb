package com.tangzhe.cslg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页 Controller
 * Created by 唐哲
 * 2017-11-27 21:25
 */
@Controller
public class HomeController {

    /** 进入首页 */
    @RequestMapping("/home")
    public String home() {
        return "home";
    }

}
