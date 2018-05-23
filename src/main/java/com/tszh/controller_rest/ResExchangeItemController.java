package com.tszh.controller_rest;

import com.tszh.cons.Code;
import com.tszh.entity.*;
import com.tszh.exception.CustomException;
import com.tszh.service.*;
import com.tszh.util.DateUtil;
import com.tszh.vo.BootstrapTableResult;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.requestVO.ExchangeBookApplyVO;
import com.tszh.vo.requestVO.ExchangeBookItemSearchVO;
import com.tszh.vo.responseVO.ResExchangeBookItemSearchVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
@Controller
@RequestMapping("/")
public class ResExchangeItemController {

    @Autowired
    UserService userService;

    @Autowired
    ExchangeItemService exchangeItemService;

    @Autowired
    ExchangeBookService exchangeBookService;

    @Autowired
    WishBookService wishBookService;

    @Autowired
    BookTypeService bookTypeService;

    @Autowired
    DateUtil dateUtil;

    @RequestMapping(value = "/exchange/doExchangeBookItemSearch",method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableResult<ResExchangeBookItemSearchVO> doExchangeBookItemSearch(@RequestBody @Valid ExchangeBookItemSearchVO exchangeBookItemSearchVO)
    {
        User user=new User();
        String email=(String)SecurityUtils.getSubject().getPrincipal();
        user.setEmail(email);
        long total=exchangeItemService.countExchangeItem(exchangeBookItemSearchVO,user);
        List<ExchangeItem> exchangeItemList=exchangeItemService.searchExchangeBookItem(exchangeBookItemSearchVO,user);
        List<ResExchangeBookItemSearchVO> resExchangeBookItemSearchVOList=new ArrayList<>();
        if(exchangeItemList!=null && exchangeItemList.size()>0){
            for(ExchangeItem ei:exchangeItemList){
                ResExchangeBookItemSearchVO rebisv=new ResExchangeBookItemSearchVO();
                rebisv.setId(ei.getId());
                rebisv.setBookName1(ei.getExchangeBook().getBookName());
                rebisv.setBookName2(ei.getWishBook().getBookName());
                rebisv.setOwner1(ei.getUser().getUsername());
                rebisv.setOwner2(ei.getWishBookOwner());
                rebisv.setApplicationDate(dateUtil.formatDate(ei.getApplicationDate(),"yyyy-MM-dd HH:mm:ss"));
                rebisv.setPassedDate(dateUtil.formatDate(ei.getPassedDate(),"yyyy-MM-dd HH:mm:ss"));
                rebisv.setExpiredDate(dateUtil.formatDate(ei.getExpiredDate(),"yyyy-MM-dd HH:mm:ss"));
                rebisv.setStatus(ei.getStatus());
                resExchangeBookItemSearchVOList.add(rebisv);
            }
        }
        BootstrapTableResult<ResExchangeBookItemSearchVO> result=new BootstrapTableResult<>(total,resExchangeBookItemSearchVOList);
        return result;
    }

    @RequestMapping(value = "/exchange/doExchangeBookApply",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doExchangeBookApply(@RequestBody @Valid ExchangeBookApplyVO exchangeBookApplyVO)throws Exception
    {
        List<ExchangeBook> l1=exchangeBookService.findByBookNameAndAuthor(exchangeBookApplyVO.getBookName2(),
                exchangeBookApplyVO.getAuthor2());
        if(l1==null || l1.size()==0)
            throw new CustomException("申请的图书不存在",Code.NOT_FOUND_ERROR.getCode());
        List<ExchangeBook> l2=exchangeBookService.findByIsbn(exchangeBookApplyVO.getIsbn2());
        if(l2==null || l2.size()==0)
            throw new CustomException("申请的图书不存在",Code.NOT_FOUND_ERROR.getCode());
        User owner=userService.findUserByUserName(exchangeBookApplyVO.getOwner2());
        if(owner==null)
            throw new CustomException("申请图书的拥有者不存在",Code.NOT_FOUND_ERROR.getCode());
        //获取当前User
        List<BookType> bookTypeList1=bookTypeService.getBookTypes(exchangeBookApplyVO.getType1());
        String userEmail=(String)SecurityUtils.getSubject().getPrincipal();
        User user=userService.findUserByEmail(userEmail);
        if(StringUtils.equals(owner.getEmail(),user.getEmail()))
            throw new CustomException("不能和自己交换图书",Code.METHOD_ARGUMENT_ERROR.getCode());
        //保存ExchangeBook eb
        ExchangeBook eb=new ExchangeBook(exchangeBookApplyVO.getBookName1(),exchangeBookApplyVO.getAuthor1(),exchangeBookApplyVO.getIsbn1(),
                exchangeBookApplyVO.getPress1(),dateUtil.parse(exchangeBookApplyVO.getPublicationDate1(),"yyyy-MM-dd"),
                exchangeBookApplyVO.getExtra1());
        eb.setBookTypes(new HashSet<>(bookTypeList1));
        eb.setUser(user);
        exchangeBookService.save(eb);

        //保存WishBook wb
        List<BookType> bookTypeList2=bookTypeService.getBookTypes(exchangeBookApplyVO.getType2());
        WishBook wb=new WishBook(exchangeBookApplyVO.getBookName2(),exchangeBookApplyVO.getAuthor2(),exchangeBookApplyVO.getIsbn2(),
                exchangeBookApplyVO.getPress2(),dateUtil.parse(exchangeBookApplyVO.getPublicationDate2(),"yyyy-MM-dd"),
                exchangeBookApplyVO.getExtra2());
        wb.setBookTypes(new HashSet<>(bookTypeList2));
        wb.setUser(user);
        wishBookService.save(wb);

        //保存ExchangeItem item
        //ExchangeItem item=new ExchangeItem(user,eb,wb,owner.getUsername(),(short) 0,new Date());
        //exchangeItemService.save(item);
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"置换申请成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

}
