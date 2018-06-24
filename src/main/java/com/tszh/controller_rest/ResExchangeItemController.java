package com.tszh.controller_rest;

import com.tszh.cons.Code;
import com.tszh.entity.*;
import com.tszh.exception.CustomException;
import com.tszh.service.*;
import com.tszh.util.DateUtil;
import com.tszh.util.NumberUtil;
import com.tszh.vo.BootstrapTableResult;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.requestVO.ExchangeBookApplyVO;
import com.tszh.vo.requestVO.ExchangeBookItemSearchVO;
import com.tszh.vo.responseVO.ResExchangeBookItemSearchVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;

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
    BookTypeService bookTypeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    @Qualifier("mailProperties")
    private Properties mailProperties;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    private NumberUtil numberUtil;

    @RequestMapping(value = "/exchange/doSearchExchangeBookItem",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public BootstrapTableResult<ResExchangeBookItemSearchVO> doSearchExchangeBookItem(@RequestBody @Valid ExchangeBookItemSearchVO exchangeBookItemSearchVO)
    {
        User user=new User();
        long total=exchangeItemService.countExchangeItem(exchangeBookItemSearchVO,user);
        List<ExchangeItem> exchangeItemList=exchangeItemService.searchExchangeBookItem(exchangeBookItemSearchVO,user);
        List<ResExchangeBookItemSearchVO> resExchangeBookItemSearchVOList=new ArrayList<>();
        if(exchangeItemList!=null && exchangeItemList.size()>0){
            for(ExchangeItem ei:exchangeItemList){
                ResExchangeBookItemSearchVO rebisv=new ResExchangeBookItemSearchVO();
                rebisv.setId(ei.getId());
                rebisv.setBookName1(ei.getMyOwnBook().getBookName());
                rebisv.setOwner1(ei.getApplyUser().getUsername());
                rebisv.setBookName2(ei.getWishBook().getBookName());
                rebisv.setOwner2(ei.getWishBookOwner().getUsername());
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

    @RequestMapping(value = "/exchange/doSearchMyExchangeBookItem",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public BootstrapTableResult<ResExchangeBookItemSearchVO> doSearchMyExchangeBookItem(@RequestBody @Valid ExchangeBookItemSearchVO exchangeBookItemSearchVO)
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
                rebisv.setBookName1(ei.getMyOwnBook().getBookName());
                rebisv.setOwner1(ei.getApplyUser().getUsername());
                rebisv.setBookName2(ei.getWishBook().getBookName());
                rebisv.setOwner2(ei.getWishBookOwner().getUsername());
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

    @RequestMapping(value = "/exchange/doDeleteExchangeBookItem/{id}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> doDeleteExchangeBookItem(@PathVariable(value = "id",required = true) String id)
    {
        if(StringUtils.isBlank(id))
            throw new CustomException("置换记录申请号不能为空",Code.METHOD_ARGUMENT_ERROR.getCode());
        ExchangeItem exchangeItem=exchangeItemService.getExchangeItemById(id);
        if(exchangeItem==null)
            throw new CustomException("id为"+id+"的置换记录不存在", Code.NOT_FOUND_ERROR.getCode());
        if(exchangeItem.getStatus()!=0 && exchangeItem.getStatus()!=2)
            throw new CustomException("置换记录中的图书正在置换中，置换记录不能删除",Code.ACCESS_DENIED_ERROR.getCode());
        exchangeItemService.deleteExchangeItemById(id);
        User applyUser=exchangeItem.getApplyUser(),wishBookOwner=exchangeItem.getWishBookOwner();
        ExchangeBook myOwnBook=exchangeItem.getMyOwnBook(),wishBook=exchangeItem.getWishBook();
        Date currentDate=new Date();
        Email applyUserEmail=new ExchangeApplyEmail(mailProperties.getProperty("mail.smtp.username"),applyUser.getEmail(),
                "图书置换系统邮件","/view/mail/book_exchange_delete_applyuser.vm",currentDate,id,
                applyUser.getUsername(),myOwnBook.getBookName(), wishBookOwner.getUsername(),wishBook.getBookName());
        Email wishBookOwnerEmail=new ExchangeApplyEmail(mailProperties.getProperty("mail.smtp.username"),wishBookOwner.getEmail(),
                "图书置换系统邮件","/view/mail/book_exchange_delete_wishbookowner.vm",currentDate,id,
                applyUser.getUsername(),myOwnBook.getBookName(), wishBookOwner.getUsername(),wishBook.getBookName());
        emailService.sendEmail(applyUserEmail);
        emailService.sendEmail(wishBookOwnerEmail);
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"置换记录删除成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchange/doApproveExchangeBookItem/{id}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("admin")
    public ResponseEntity<ResponseTemplate> doApproveExchangeBookItem(@PathVariable(value = "id",required = true) String id)
    {
        if(StringUtils.isBlank(id))
            throw new CustomException("置换记录申请号不能为空",Code.METHOD_ARGUMENT_ERROR.getCode());
        ExchangeItem exchangeItem=exchangeItemService.getExchangeItemById(id);
        if(exchangeItem==null)
            throw new CustomException("id为"+id+"的置换记录不存在", Code.NOT_FOUND_ERROR.getCode());
        if(exchangeItem.getStatus()!=0)
            throw new CustomException("置换记录的状态不是待审核，无法通过",Code.ACCESS_DENIED_ERROR.getCode());
        int n=exchangeItemService.approveExchangeItemById(id);
        if(n<=0)
            throw new CustomException("通过置换记录失败",Code.DATABASE_ACCESS_ERROR.getCode());
        User applyUser=exchangeItem.getApplyUser(),wishBookOwner=exchangeItem.getWishBookOwner();
        ExchangeBook myOwnBook=exchangeItem.getMyOwnBook(),wishBook=exchangeItem.getWishBook();
        Date currentDate=new Date();
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.add(calendar.MONTH,2);
        Date expireDate=calendar.getTime();
        Email applyUserEmail=new ExchangeApplyEmail(mailProperties.getProperty("mail.smtp.username"),applyUser.getEmail(),
                "图书置换系统邮件","/view/mail/book_exchange_approve_applyuser.vm",currentDate,id,
                applyUser.getUsername(),myOwnBook.getBookName(), wishBookOwner.getUsername(),wishBook.getBookName(),expireDate,
                "四川省成都市高新区西源大道2006号");
        Email wishBookOwnerEmail=new ExchangeApplyEmail(mailProperties.getProperty("mail.smtp.username"),wishBookOwner.getEmail(),
                "图书置换系统邮件","/view/mail/book_exchange_approve_wishbookowner.vm",currentDate,id,
                applyUser.getUsername(),myOwnBook.getBookName(), wishBookOwner.getUsername(),wishBook.getBookName(),expireDate,
                "四川省成都市高新区西源大道2006号");
        emailService.sendEmail(applyUserEmail);
        emailService.sendEmail(wishBookOwnerEmail);
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"置换记录通过成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchange/doExpireExchangeBookItem/{id}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("admin")
    public ResponseEntity<ResponseTemplate> doExpireExchangeBookItem(@PathVariable(value = "id",required = true) String id)
    {
        if(StringUtils.isBlank(id))
            throw new CustomException("置换记录申请号不能为空",Code.METHOD_ARGUMENT_ERROR.getCode());
        ExchangeItem exchangeItem=exchangeItemService.getExchangeItemById(id);
        if(exchangeItem==null)
            throw new CustomException("id为"+id+"的置换记录不存在", Code.NOT_FOUND_ERROR.getCode());
        if(exchangeItem.getStatus()!=1)
            throw new CustomException("置换记录的状态不是已通过，无法过期",Code.ACCESS_DENIED_ERROR.getCode());
        exchangeItemService.expireExchangeItemById(id);
        User applyUser=exchangeItem.getApplyUser(),wishBookOwner=exchangeItem.getWishBookOwner();
        ExchangeBook myOwnBook=exchangeItem.getMyOwnBook(),wishBook=exchangeItem.getWishBook();
        Date currentDate=new Date();
        Email applyUserEmail=new ExchangeApplyEmail(mailProperties.getProperty("mail.smtp.username"),applyUser.getEmail(),
                "图书置换系统邮件","/view/mail/book_exchange_expire_applyuser.vm",currentDate,id,
                applyUser.getUsername(),myOwnBook.getBookName(), wishBookOwner.getUsername(),wishBook.getBookName());
        Email wishBookOwnerEmail=new ExchangeApplyEmail(mailProperties.getProperty("mail.smtp.username"),wishBookOwner.getEmail(),
                "图书置换系统邮件","/view/mail/book_exchange_expire_wishbookowner.vm",currentDate,id,
                applyUser.getUsername(),myOwnBook.getBookName(), wishBookOwner.getUsername(),wishBook.getBookName());
        emailService.sendEmail(applyUserEmail);
        emailService.sendEmail(wishBookOwnerEmail);
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"已使置换记录过期");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchange/doExchangeBookApply",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("user")
    public ResponseEntity<ResponseTemplate> doExchangeBookApply(@RequestBody @Valid ExchangeBookApplyVO exchangeBookApplyVO)throws Exception
    {
        String email=(String)SecurityUtils.getSubject().getPrincipal();
        User user=userService.findUserByEmail(email);
        if(user.getDeposit()<=0)
            throw new CustomException("账户金额不足，请充值",Code.ACCESS_DENIED_ERROR.getCode());
        ExchangeBook myOwnBook,wishBook;
        ExchangeItem exchangeItem;
        Serializable id;
        wishBook=exchangeBookService.findCanExchangeBookFetchUserById(exchangeBookApplyVO.getWishBookId());
        if(wishBook==null)
            throw new CustomException("申请置换的图书不存在",Code.NOT_FOUND_ERROR.getCode());
        if(exchangeBookApplyVO.getMyOwnBookId()!=null){
           myOwnBook=exchangeBookService.findCanExchangeBookFetchUserById(exchangeBookApplyVO.getMyOwnBookId());
           if(myOwnBook==null)
               throw new CustomException("用于置换的图书不存在",Code.NOT_FOUND_ERROR.getCode());
           if(StringUtils.equals(myOwnBook.getUser().getEmail(),wishBook.getUser().getEmail()))
                throw new CustomException("置换申请失败，不能和自己交换图书",Code.ACCESS_DENIED_ERROR.getCode());
           if(myOwnBook.isCanExchange() && wishBook.isCanExchange()) {
               exchangeItem = new ExchangeItem(myOwnBook, myOwnBook.getUser(), wishBook, wishBook.getUser(), (short) 0, new Date());
               id=exchangeItemService.save(exchangeItem);
               if(id==null)
                   throw new CustomException("置换申请失败",Code.DATABASE_ACCESS_ERROR.getCode());
           }else{
               throw new CustomException("有图书在交换状态，置换申请失败",Code.ACCESS_DENIED_ERROR.getCode());
           }
        }else{
            myOwnBook=new ExchangeBook(exchangeBookApplyVO.getBookName(),exchangeBookApplyVO.getAuthor(),exchangeBookApplyVO.getIsbn(),
                    exchangeBookApplyVO.getPress(),dateUtil.parse(exchangeBookApplyVO.getPublicationDate(),"yyyy-MM-dd"),exchangeBookApplyVO.getExtra());
            List<BookType> bookTypeList=bookTypeService.getBookTypes(exchangeBookApplyVO.getTypes());
            myOwnBook.setBookTypes(new HashSet<>(bookTypeList));
            Calendar calendar= Calendar.getInstance();
            myOwnBook.setReleaseDate(calendar.getTime());//设置发布时间
            //String email=(String)SecurityUtils.getSubject().getPrincipal();
            //User user=userService.findUserByEmail(email);
            myOwnBook.setUser(user);//设置拥有用户
            if(StringUtils.equals(email,wishBook.getUser().getEmail()))
                throw new CustomException("置换申请失败，不能和自己交换图书",Code.ACCESS_DENIED_ERROR.getCode());
            if(wishBook.isCanExchange()) {
                exchangeItem = new ExchangeItem(myOwnBook, myOwnBook.getUser(), wishBook, wishBook.getUser(), (short) 0, new Date());
                id=exchangeItemService.save(exchangeItem);
                if(id==null)
                    throw new CustomException("置换申请失败",Code.DATABASE_ACCESS_ERROR.getCode());
            }else{
                throw new CustomException("有图书在交换状态，置换申请失败",Code.ACCESS_DENIED_ERROR.getCode());
            }
        }
        Date currentDate=new Date();
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.add(calendar.DATE,14);
        Date expireDate=calendar.getTime();
        User applyUser=myOwnBook.getUser(), wishBookOwner=wishBook.getUser();
        Email applyUserEmail=new ExchangeApplyEmail(mailProperties.getProperty("mail.smtp.username"),applyUser.getEmail(),
                "图书置换系统邮件","/view/mail/book_exchange_apply_applyuser.vm",currentDate,id.toString(),
                applyUser.getUsername(),myOwnBook.getBookName(), wishBookOwner.getUsername(),wishBook.getBookName(),expireDate,
                "四川省成都市高新区西源大道2006号");
        Email wishBookOwnerEmail=new ExchangeApplyEmail(mailProperties.getProperty("mail.smtp.username"),wishBookOwner.getEmail(),
                "图书置换系统邮件","/view/mail/book_exchange_apply_wishbookowner.vm",currentDate,id.toString(),
                applyUser.getUsername(),myOwnBook.getBookName(), wishBookOwner.getUsername(),wishBook.getBookName(),expireDate,
                "四川省成都市高新区西源大道2006号");
        emailService.sendEmail(applyUserEmail);
        emailService.sendEmail(wishBookOwnerEmail);
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"置换申请已成功提交");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

}
