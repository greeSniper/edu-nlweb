package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.Teacher;
import com.tangzhe.cslg.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录 Controller
 */
@Controller
public class LoginController {

    private static final String HOME = "common/index";
    private static final String COMMON = "common/pre";

    @Autowired
    private TeacherService teacherService;

    /**
     * 进入登录页面
     */
    @RequestMapping("/tologin")
    public String tologin() {
        return "login";
    }

    /**
     * 登录功能
     */
    @RequestMapping("/login")
    public String login(String checkcode, Teacher teacher, HttpServletRequest request) {
        String validatecode = (String) request.getSession().getAttribute("key");
        Teacher user = null;
        if(StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)) {
            user = teacherService.login(teacher);
            if(user == null) {
                request.setAttribute("msg", "用户名或密码错误！");
                return "login";
            }
            request.getSession().setAttribute("loginUser", user);
        } else {
            request.setAttribute("msg", "验证码错误！");
            return "login";
        }

        //判断当前用户是否为管理员
        if(user.getType() == 0) {
            //管理员
            return HOME;
        } else {
            //普通用户
            return COMMON;
        }
    }

    /**
     * 用户登出
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/editPassword")
    @ResponseBody
    public String editPassword(Teacher user, HttpSession session) {
        String flag = "1";

        Teacher loginUser = (Teacher) session.getAttribute("loginUser");
        loginUser.setPassword(user.getPassword());

        try {
            teacherService.editPassword(loginUser);
        } catch (Exception e) {
            flag = "0";
            e.printStackTrace();
        }

        return flag;
    }

}
