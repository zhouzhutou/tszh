package com.tszh.controller_rest;

import com.tszh.cons.Code;
import com.tszh.entity.Address;
import com.tszh.entity.User;
import com.tszh.exception.CustomException;
import com.tszh.service.AddressService;
import com.tszh.service.UserService;
import com.tszh.util.DateUtil;
import com.tszh.util.NumberUtil;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.requestVO.AddressVO;
import com.tszh.vo.requestVO.RechargeAccountVO;
import com.tszh.vo.requestVO.UserModifyVO;
import com.tszh.vo.responseVO.ResAddressVO;
import com.tszh.vo.responseVO.ResGetMyAccountInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

/**
 * Created by Administrator on 2018/5/30 0030.
 */
@Controller
@RequestMapping("/")
public class ResUserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    NumberUtil numberUtil;

    @RequestMapping(value = "/user/doGetMyAccountInfo/{id}",method= RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("user")
    public ResponseEntity<ResponseTemplate> doGetMyAccountInfo(@PathVariable(value = "id",required = true) String id){
        if(!numberUtil.isPosNum(id))
            throw new CustomException("参数校验失败",Code.METHOD_ARGUMENT_ERROR.getCode());
        int userId=Integer.valueOf(id);
        User user=userService.findUserById(userId);
        if(user==null)
            throw new CustomException("找不到你的用户信息", Code.NOT_FOUND_ERROR.getCode());
        Address address=user.getAddress();
        ResAddressVO resAddressVO=null;
        if(address!=null)
            resAddressVO=new ResAddressVO(address.getProvince(),address.getCity(),address.getCounty(),address.getStreet());
        ResGetMyAccountInfoVO resGetMyAccountInfoVO=new ResGetMyAccountInfoVO(user.getId(),user.getEmail(),user.getUsername(),user.getSex(),
                dateUtil.formatDate(user.getBirthday(),"yyyy-MM-dd"),user.getPhone(),resAddressVO,user.getDeposit());
        ResponseTemplate<ResGetMyAccountInfoVO> responseTemplate=new ResponseTemplate<ResGetMyAccountInfoVO>(Code.SUCCESS.getCode(),resGetMyAccountInfoVO);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/user/doModifyUser",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> doModifyUser(@RequestBody @Valid UserModifyVO userModifyVO)throws ParseException
    {
        User existUser=userService.findUserByUserName(userModifyVO.getUsername());
        if(existUser!=null && !StringUtils.equals(existUser.getUsername(),userModifyVO.getUsername()))
            throw new CustomException("该用户名已被使用",Code.USERNAME_EXIST_ERROR.getCode());
        User user=userService.findUserById(userModifyVO.getId());
        user.setUsername(userModifyVO.getUsername());
        user.setPhone(userModifyVO.getPhone());
        user.setBirthday(dateUtil.parse(userModifyVO.getBirthday(),"yyyy-MM-dd"));
        user.setSex(userModifyVO.getSex());
        AddressVO addressVO=userModifyVO.getAddressVO();
        Address address=user.getAddress();
        if(addressVO!=null){
            if(address!=null){
                address.setProvince(addressVO.getProvince());
                address.setCity(addressVO.getCity());
                address.setCounty(addressVO.getCounty());
                address.setStreet(addressVO.getStreet());
                address.setUser(user);
                addressService.update(address);
            }else{
                address=new Address(addressVO.getProvince(),addressVO.getCity(),addressVO.getCounty(),addressVO.getStreet());
                address.setUser(user);
                addressService.save(address);
            }
        }
        //user.setAddress(address);
        userService.update(user);
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"个人信息修改成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return  responseEntity;
    }

    @RequestMapping(value = "/user/doRechargeAccount",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("user")
    public ResponseEntity<ResponseTemplate> doRechargeAccount(@RequestBody @Valid RechargeAccountVO rechargeAccountVO)
    {
        int n=userService.addDeposit(rechargeAccountVO);
        String message;
        if(n<=0)
            throw new CustomException("充值失败",Code.DATABASE_ACCESS_ERROR.getCode());
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"充值成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

}
