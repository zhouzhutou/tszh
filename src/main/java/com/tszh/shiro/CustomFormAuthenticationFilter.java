package com.tszh.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/25 0025.
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {


    private UsernamePasswordToken getUsernamePasswordToken(ServletRequest request, ServletResponse response)
    {
        String email=request.getParameter("username");
        String password=request.getParameter("password");
        return new UsernamePasswordToken(email,password);
    }


    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        UsernamePasswordToken token=getUsernamePasswordToken(request,response);
        Subject subject=getSubject(request,response);
        try {
            System.out.println("hello");
            subject.login(token);
            System.out.println("hello");
            return onLoginSuccess(token,subject,request,response);
        }catch (UnknownAccountException e)
        {
            e.printStackTrace();
            return onLoginFailure(token,e,request,response);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            return onAccessDenied(request, response);
        } catch (ExpiredCredentialsException e) {
            e.printStackTrace();
            return onLoginFailure(token, e, request, response);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return onLoginFailure(token, e, request, response);
        } catch (LockedAccountException e) {
            e.printStackTrace();
            return onLoginFailure(token, e, request, response);
        } catch (DisabledAccountException e) {
            e.printStackTrace();
            return onLoginFailure(token, e, request, response);
        } catch (ExcessiveAttemptsException e) {
            e.printStackTrace();
            return onLoginFailure(token, e, request, response);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return onLoginFailure(token, e, request, response);
        }
    }
}
