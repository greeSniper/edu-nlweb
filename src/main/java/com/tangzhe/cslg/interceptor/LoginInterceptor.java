package com.tangzhe.cslg.interceptor;

import com.tangzhe.cslg.entity.Teacher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器实现用户未登录自动跳转到登录页面
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //请求地址带有login直接放行
        if(request.getRequestURI().contains("login") ||
                request.getRequestURI().contains("error") ||
                request.getRequestURI().contains("home")) {
            return true;
        }

        //获取session中userInfo
        Teacher user = (Teacher) request.getSession().getAttribute("loginUser");

        //判断user是否为空
        if(user != null) {
            //如果user不为空，放行
            return true;
        }
        //如果user为空，请求转发至登录页面
        request.getRequestDispatcher("/tologin").forward(request, response);

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
