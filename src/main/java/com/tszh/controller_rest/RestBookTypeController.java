package com.tszh.controller_rest;

import com.tszh.cons.Code;
import com.tszh.entity.BookType;
import com.tszh.exception.CustomException;
import com.tszh.service.BookTypeService;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.responseVO.ResGetBookTypesVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
@Controller
@RequestMapping("/")
public class RestBookTypeController {

    @Autowired
    BookTypeService bookTypeService;

    @RequestMapping(value = "/bookType/getBookTypes",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> getBookTypes(HttpServletRequest request, HttpServletResponse response)
    {
        List<BookType> bookTypeList=bookTypeService.getBookTypes();
        List<ResGetBookTypesVO> resGetBookTypesVOList=new ArrayList<>();
        if(bookTypeList!=null && bookTypeList.size()>0)
        {
            for(BookType bt:bookTypeList){
                ResGetBookTypesVO resGetBookTypesVO=new ResGetBookTypesVO();
                resGetBookTypesVO.setId(bt.getId());
                resGetBookTypesVO.setType(bt.getType());
                resGetBookTypesVOList.add(resGetBookTypesVO);
            }
        }
        ResponseTemplate<List<ResGetBookTypesVO>> responseTemplate=new ResponseTemplate<List<ResGetBookTypesVO>>(Code.SUCCESS.getCode(),
                resGetBookTypesVOList);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

}
