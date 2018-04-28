package com.tszh.intercepter;

import com.tszh.entity.Role;
import com.tszh.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/4/27 0027.
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String servletPath=httpServletRequest.getServletPath();
        if(StringUtils.equals(servletPath,"/login") || StringUtils.equals(servletPath,"/login/")  ||
                StringUtils.equals(servletPath,"/") )
            return true;
        User user=(User)httpServletRequest.getAttribute("currentUser");
        if(user!=null) {
            Role role = user.getRole();
            if (role != null || StringUtils.equals(role.getRoleName(),"user") || StringUtils.equals(role.getRoleName(),"admin") )
                return true;
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
