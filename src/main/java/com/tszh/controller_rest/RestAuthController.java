package com.tszh.controller_rest;

import com.tszh.cons.Code;
import com.tszh.entity.Email;
import com.tszh.entity.Role;
import com.tszh.entity.User;
import com.tszh.entity.VerifyEmail;
import com.tszh.exception.CustomException;
import com.tszh.redis_cache.RedisCache;
import com.tszh.service.EmailService;
import com.tszh.service.RoleService;
import com.tszh.service.UserService;
import com.tszh.util.CryptographyUtil;
import com.tszh.util.DateUtil;
import com.tszh.util.GenAuthCodeUtil;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.requestVO.*;
import com.tszh.vo.responseVO.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2018/5/3 0003.
 */
@Controller
@RequestMapping("/")
public class RestAuthController {

    @Autowired
    private CryptographyUtil cryptographyUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private GenAuthCodeUtil genAuthCodeUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    @Qualifier("mailProperties")
    private Properties mailProperties;

    @Autowired
    private DateUtil dateUtil;

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doLogin(@RequestBody @Valid LoginVO loginVO, HttpServletRequest request, HttpServletResponse servletResponse)
    {
        String loginEmail=loginVO.getEmail();
        String loginPassword=loginVO.getPassword();
        //System.out.println("loginEmail:"+loginEmail+" "+"loginPassword:"+loginPassword);
        UsernamePasswordToken token=new UsernamePasswordToken(loginEmail,loginPassword);
        Subject subject=SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (Exception e) {
            throw new CustomException("邮箱或密码错误", Code.EMAIL_OR_PASSWORD_ERROR.getCode());
        }
        User user=userService.findUserByEmail(loginEmail);
        user.setLastLoginDate(new Date());
        userService.update(user);
        ResLoginVO resLoginVO=new ResLoginVO(user.getId(),user.getEmail(),user.getUsername(),user.getSex(),
                user.getRole().getRoleName(),dateUtil.formatDate(user.getLastLoginDate(),"yyyy-MM-dd HH:mm:ss"));
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"登陆成功",resLoginVO);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/doLogout",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> doLogout(HttpServletRequest request, HttpServletResponse servletResponse)
    {
        Subject subject=SecurityUtils.getSubject();
        if(subject!=null)
            subject.logout();
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"注销成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/doRegister",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doRegister(@RequestBody @Valid RegisterVO registerVO) throws InterruptedException
    {
        int c=userService.countByEmail(registerVO.getEmail());
        if(c>0)
            throw new CustomException("该邮箱已被注册",Code.EMAIL_EXIST_ERROR.getCode());
        User userNameUser=userService.findUserByUserName(registerVO.getUsername());
        if(userNameUser!=null)
            throw new CustomException("该用户名已被注册",Code.USERNAME_EXIST_ERROR.getCode());
        String registerVerifyCode=genAuthCodeUtil.getAuthCode(6);
        redisCache.put(registerVO.getEmail(),registerVerifyCode,120);
        redisCache.put(registerVO.getEmail()+"_personInfo",registerVO,120);
        Email email=new VerifyEmail(mailProperties.getProperty("mail.smtp.username"),registerVO.getEmail(),
                "图书置换系统用户注册邮件","/view/mail/register.vm",new Date(),registerVerifyCode);
        emailService.sendEmail(email);
        ResRegisterVO resRegisterVO=new ResRegisterVO(registerVO.getEmail(),registerVO.getUsername(),registerVO.getGender());
        ResponseTemplate<ResRegisterVO> responseTemplate=new ResponseTemplate<ResRegisterVO>(Code.SUCCESS.getCode(),
                "获取验证码成功",resRegisterVO);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value="/doMatchRegisterVerifyCode",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doMatchRegisterVerifyCode(@RequestBody @Valid MatchRegisterVerifyCodeVO matchRegisterVerifyCodeVO)
    {
        String verifyCode=redisCache.get(matchRegisterVerifyCodeVO.getEmail(),String.class);
        if(StringUtils.isBlank(verifyCode) || !StringUtils.equals(matchRegisterVerifyCodeVO.getRegisterVerifyCode(),verifyCode))
            throw new CustomException("验证码验证失败",Code.VERIFY_CODE_NOT_MATCH_ERROR.getCode());
        RegisterVO registerVO=redisCache.get(matchRegisterVerifyCodeVO.getEmail()+"_personInfo",RegisterVO.class);
        User user =new User();
        user.setEmail(registerVO.getEmail());
        user.setUsername(registerVO.getUsername());
        user.setSex(registerVO.getGender());
        user.setDeposit(0);
        RandomNumberGenerator rng=new SecureRandomNumberGenerator();
        user.setSalt(rng.nextBytes().toString());
        user.setPassword(cryptographyUtil.md5(registerVO.getPassword(),ByteSource.Util.bytes(user.getSalt()),1));
        Role role=roleService.getRoleByName("user");
        if(role==null) {
            role = new Role();
            role.setRoleName("user");
            Serializable id=roleService.save(role);
            if(id==null)
                throw new CustomException("数据存储异常",Code.DATABASE_ACCESS_ERROR.getCode());
        }
        user.setRole(role);
        Serializable id=userService.save(user);
        if(id==null)
            throw new CustomException("注册失败",Code.DATABASE_ACCESS_ERROR.getCode());
        ResMatchRegisterVerifyCodeVO resMatchRegisterVerifyCodeVO=new ResMatchRegisterVerifyCodeVO(user.getId(),user.getEmail(),
                user.getUsername(),registerVO.getPassword(),user.getSex());
        ResponseTemplate<ResMatchRegisterVerifyCodeVO> responseTemplate=new ResponseTemplate<ResMatchRegisterVerifyCodeVO>(Code.SUCCESS.getCode(),
                "验证码验证通过，注册成功",resMatchRegisterVerifyCodeVO);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/doForgetPassword",method =RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doForgetPassword(@RequestBody @Valid ForgetPasswordVO forgetPasswordVO) throws InterruptedException
    {
        User user=userService.findUserByEmail(forgetPasswordVO.getEmail());
        if(user==null)
            throw new CustomException("该邮箱未被注册",Code.EMAIL_NOT_EXIST_ERROR.getCode());
        String forgetPasswordVerifyCode=genAuthCodeUtil.getAuthCode(6);
        redisCache.put(forgetPasswordVO.getEmail(),forgetPasswordVerifyCode,120);
        Email email=new VerifyEmail(mailProperties.getProperty("mail.smtp.username"),forgetPasswordVO.getEmail(),
                "图书置换系统重置密码邮件","/view/mail/forgetPassword.vm",new Date(),forgetPasswordVerifyCode,
                user.getUsername());
        emailService.sendEmail(email);
        ResForgetPasswordVO resForgetPasswordVO=new ResForgetPasswordVO(forgetPasswordVO.getEmail());
        ResponseTemplate<ResForgetPasswordVO> responseTemplate=new ResponseTemplate<ResForgetPasswordVO>(Code.SUCCESS.getCode(),
                "获取验证码成功",resForgetPasswordVO);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/doResetPassword",method =RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doResetPassword(@RequestBody @Valid ResetPasswordVO resetPasswordVO)
    {
        String verifyCode=redisCache.get(resetPasswordVO.getEmail(),String.class);
        if(StringUtils.isBlank(verifyCode) || !StringUtils.equals(resetPasswordVO.getForgetPasswordVerifyCode(),verifyCode))
            throw new CustomException("验证码验证失败",Code.VERIFY_CODE_NOT_MATCH_ERROR.getCode());
        User user=userService.findUserByEmail(resetPasswordVO.getEmail());
        if(user==null)
            throw new CustomException("该邮箱未被注册",Code.EMAIL_NOT_EXIST_ERROR.getCode());
        RandomNumberGenerator rng=new SecureRandomNumberGenerator();
        user.setSalt(rng.nextBytes().toString());
        user.setPassword(cryptographyUtil.md5(resetPasswordVO.getPassword(),ByteSource.Util.bytes(user.getSalt()),1));
        userService.update(user);
        ResResetPasswordVO resResetPasswordVO=new ResResetPasswordVO(resetPasswordVO.getEmail(),resetPasswordVO.getPassword());
        ResponseTemplate<ResResetPasswordVO> responseTemplate=new ResponseTemplate<ResResetPasswordVO>(Code.SUCCESS.getCode(),
                "密码重置成功",resResetPasswordVO);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    /*@RequestMapping(value = "/doRegister",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doRegister(@RequestBody @Valid RegisterVO registerVO)
    {
        int c=userService.countByEmail(registerVO.getEmail());
        if(c>0)
            throw new CustomException("该邮箱已被注册",Code.EMAIL_EXIST_ERROR.getCode());
        User usernameU=userService.findUserByUserName(registerVO.getUsername());
        if(usernameU!=null)
            throw new CustomException("该用户名已被注册",Code.USERNAME_EXIST_ERROR.getCode());
        User user=new User();
        user.setEmail(registerVO.getEmail());
        user.setUsername(registerVO.getUsername());
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        user.setSalt(randomNumberGenerator.nextBytes().toString());
        user.setPassword(cryptographyUtil.md5(registerVO.getPassword(), ByteSource.Util.bytes(user.getSalt()),1));
        user.setSex(registerVO.getGender());
        user.setDeposit(0);
        Role role=roleService.getRoleByName("user");
        user.setRole(role);
        Serializable id=userService.save(user);
        if(id==null)
            throw new CustomException("注册失败",Code.DATABASE_ACCESS_ERROR.getCode());
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"注册成功",registerVO);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }*/
}
