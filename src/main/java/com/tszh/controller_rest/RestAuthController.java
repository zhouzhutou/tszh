package com.tszh.controller_rest;

import com.tszh.cons.Code;
import com.tszh.entity.Role;
import com.tszh.entity.User;
import com.tszh.exception.CustomException;
import com.tszh.service.RoleService;
import com.tszh.service.UserService;
import com.tszh.util.CryptographyUtil;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.requestVO.LoginVO;
import com.tszh.vo.requestVO.RegisterVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
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
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"登陆成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/doRegister",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doRegister(@RequestBody @Valid RegisterVO registerVO)
    {
        int c=userService.countByEmail(registerVO.getEmail());
        if(c>0)
            throw new CustomException("该邮箱已被注册",Code.EMAIL_EXIST_ERROR.getCode());
        User usernameU=userService.findUserByUserName(registerVO.getUsername());
        if(usernameU!=null)
            throw new CustomException("该昵称已被注册",Code.USERNAME_EXIST_ERROR.getCode());
        User user=new User();
        user.setEmail(registerVO.getEmail());
        user.setUsername(registerVO.getUsername());
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        user.setSalt(randomNumberGenerator.nextBytes().toString());
        user.setPassword(cryptographyUtil.md5(registerVO.getPassword(), ByteSource.Util.bytes(user.getSalt()),1));
        user.setSex(Byte.valueOf(registerVO.getGender()));
        Role role=roleService.getRoleByName("user");
        user.setRole(role);
        Serializable id=userService.save(user);
        if(id==null)
            throw new CustomException("注册失败",Code.REGISTER_ERROR.getCode());
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"注册成功",registerVO);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }
}
