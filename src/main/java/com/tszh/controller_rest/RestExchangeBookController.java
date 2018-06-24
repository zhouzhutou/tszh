package com.tszh.controller_rest;

import com.tszh.cons.Code;
import com.tszh.entity.*;
import com.tszh.exception.CustomException;
import com.tszh.service.BookTypeService;
import com.tszh.service.ExchangeBookService;
import com.tszh.service.ExchangeItemService;
import com.tszh.service.UserService;
import com.tszh.util.DateUtil;
import com.tszh.util.NumberUtil;
import com.tszh.vo.BootstrapTableResult;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.requestVO.*;
import com.tszh.vo.responseVO.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@Controller
@RequestMapping("/")
public class RestExchangeBookController {

    @Autowired
    ExchangeBookService exchangeBookService;

    @Autowired
    ExchangeItemService exchangeItemService;

    @Autowired
    UserService userService;

    @Autowired
    BookTypeService bookTypeService;

    @Autowired
    NumberUtil numberUtil;

    @Autowired
    DateUtil dateUtil;

    /**
     * 查询ExchangeBook
     * @param bookSearchVO
     * @return
     */
    @RequestMapping(value = "/exchangeBook/doSearchBook",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public BootstrapTableResult<ResBookSearchVO> doSearchBook(@RequestBody BookSearchVO bookSearchVO)
    {
        if(bookSearchVO.getPageSize()<=0 || bookSearchVO.getPageNumber()<=0)
            throw new CustomException("分页参数错误",Code.METHOD_ARGUMENT_ERROR.getCode());
        long total=exchangeBookService.countExchangeBook(bookSearchVO,null);
        List<ExchangeBook> exchangeBookList=exchangeBookService.searchExchangeBook(bookSearchVO,null);
        List<ResBookSearchVO> resBookSearchVOList=new ArrayList<>();
        if(exchangeBookList!=null && exchangeBookList.size()>0)
        {
            for(ExchangeBook eb:exchangeBookList)
            {
                ResBookSearchVO rbsv=new ResBookSearchVO();
                rbsv.setId(eb.getId());
                rbsv.setBookName(eb.getBookName());
                rbsv.setAuthor(eb.getAuthor());
                rbsv.setISBN(eb.getISBN());
                rbsv.setPress(eb.getPress());
                rbsv.setOwner(eb.getUser().getUsername());
                rbsv.setPublicationDate(dateUtil.formatDate(eb.getPublicationDate(),"yyyy-MM-dd"));
                rbsv.setReleaseDate(dateUtil.formatDate(eb.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
                rbsv.setCanExchange(eb.isCanExchange());
                Set<BookType> bookTypeSet=eb.getBookTypes();
                List<String> types=new ArrayList<>();
                if(bookTypeSet!=null && bookTypeSet.size()>0) {
                    for (BookType bookType : bookTypeSet)
                        types.add(bookType.getType());
                }
                rbsv.setTypes(types);
                resBookSearchVOList.add(rbsv);
            }
        }
        BootstrapTableResult<ResBookSearchVO> result=new BootstrapTableResult<>(total,resBookSearchVOList);
        return result;
    }

    @RequestMapping(value = "/exchangeBook/doMySearchBook",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("user")
    public BootstrapTableResult<ResBookSearchVO> doMySearchBook(@RequestBody BookSearchVO bookSearchVO)
    {
        if(bookSearchVO.getPageSize()<=0 || bookSearchVO.getPageNumber()<=0)
            throw new CustomException("分页参数错误",Code.METHOD_ARGUMENT_ERROR.getCode());
        String email= (String)SecurityUtils.getSubject().getPrincipal();
        User user=new User();
        user.setEmail(email);
        long total=exchangeBookService.countExchangeBook(bookSearchVO,user);
        List<ExchangeBook> exchangeBookList=exchangeBookService.searchExchangeBook(bookSearchVO,user);
        List<ResBookSearchVO> resBookSearchVOList=new ArrayList<>();
        if(exchangeBookList!=null && exchangeBookList.size()>0)
        {
            for(ExchangeBook eb:exchangeBookList)
            {
                ResBookSearchVO rbsv=new ResBookSearchVO();
                rbsv.setId(eb.getId());
                rbsv.setBookName(eb.getBookName());
                rbsv.setAuthor(eb.getAuthor());
                rbsv.setISBN(eb.getISBN());
                rbsv.setPress(eb.getPress());
                rbsv.setPublicationDate(dateUtil.formatDate(eb.getPublicationDate(),"yyyy-MM-dd"));
                rbsv.setReleaseDate(dateUtil.formatDate(eb.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
                rbsv.setCanExchange(eb.isCanExchange());
                Set<BookType> bookTypeSet=eb.getBookTypes();
                List<String> types=new ArrayList<>();
                if(bookTypeSet!=null && bookTypeSet.size()>0) {
                    for (BookType bookType : bookTypeSet)
                        types.add(bookType.getType());
                }
                rbsv.setTypes(types);
                resBookSearchVOList.add(rbsv);
            }
        }
        BootstrapTableResult<ResBookSearchVO> result=new BootstrapTableResult<>(total,resBookSearchVOList);
        return result;
    }

    /**
     * 获取登录用户的可交换图书
     * @return
     */
    @RequestMapping(value = "/exchangeBook/doSearchMyCanExchangeBook",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public BootstrapTableResult<ResBookSearchVO> doSearchMyCanExchangeBook(@RequestBody @Valid BookSearchVO bookSearchVO)
    {
        if(bookSearchVO.getPageSize()<=0 || bookSearchVO.getPageNumber()<=0)
            throw new CustomException("分页参数错误",Code.METHOD_ARGUMENT_ERROR.getCode());
        String email=(String)SecurityUtils.getSubject().getPrincipal();
        User user=new User();
        user.setEmail(email);
        bookSearchVO.setCanExchange(true);
        long total=exchangeBookService.countExchangeBook(bookSearchVO,user);
        List<ExchangeBook> exchangeBookList=exchangeBookService.searchExchangeBook(bookSearchVO,user);
        List<ResBookSearchVO> resBookSearchVOList=new ArrayList<>();
        if(exchangeBookList!=null && exchangeBookList.size()>0)
        {
            for(ExchangeBook eb:exchangeBookList)
            {
                ResBookSearchVO rbsv=new ResBookSearchVO();
                rbsv.setId(eb.getId());
                rbsv.setBookName(eb.getBookName());
                rbsv.setAuthor(eb.getAuthor());
                rbsv.setISBN(eb.getISBN());
                rbsv.setPress(eb.getPress());
                rbsv.setPublicationDate(dateUtil.formatDate(eb.getPublicationDate(),"yyyy-MM-dd"));
                rbsv.setReleaseDate(dateUtil.formatDate(eb.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
                rbsv.setCanExchange(eb.isCanExchange());
                Set<BookType> bookTypeSet=eb.getBookTypes();
                List<String> types=new ArrayList<>();
                if(bookTypeSet!=null && bookTypeSet.size()>0) {
                    for (BookType bookType : bookTypeSet)
                        types.add(bookType.getType());
                }
                rbsv.setTypes(types);
                resBookSearchVOList.add(rbsv);
            }
        }
        BootstrapTableResult<ResBookSearchVO> result=new BootstrapTableResult<>(total,resBookSearchVOList);
        return result;
    }

    /**
     * 查询指定id图书的信息
     * @return
     */
    @RequestMapping(value = "/exchangeBook/bookInfo/{id}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> getSearchBookById(@PathVariable(value = "id",required = true) String id)
    {
        if(!numberUtil.isPosNum(id))
            throw new CustomException("参数校验失败",Code.METHOD_ARGUMENT_ERROR.getCode());
        int bookId=Integer.valueOf(id);
        ExchangeBook eb=exchangeBookService.getExchangeBookById(bookId);
        if(eb==null)
            throw new CustomException("id为"+id+"的图书不存在",Code.NOT_FOUND_ERROR.getCode());
        Set<BookType> bookTypeSet=eb.getBookTypes();
        List<String> types=new ArrayList<>();
        if(bookTypeSet!=null && bookTypeSet.size()>0) {
            for (BookType bookType : bookTypeSet)
                types.add(bookType.getType());
        }
        ResExchangeBookInfoVO rebiv=new ResExchangeBookInfoVO(eb.getId(),eb.getBookName(),eb.getAuthor(),eb.getISBN(),
                types,eb.getPress(),eb.getPublicationDate(),eb.getUser().getUsername(),eb.getExtra(),eb.isCanExchange(),
                dateUtil.formatDate(eb.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
        ResponseTemplate<ResExchangeBookInfoVO> responseTemplate=new ResponseTemplate<ResExchangeBookInfoVO>(Code.SUCCESS.getCode(),rebiv);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }
    /*----------------above change-----------------*/

    @RequestMapping(value = "/exchangeBook/myBookInfo/{id}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("user")
    public ResponseEntity<ResponseTemplate> getMySearchBookById(@PathVariable(value = "id",required = true) String id)
    {
        if(!numberUtil.isPosNum(id))
            throw new CustomException("参数校验失败",Code.METHOD_ARGUMENT_ERROR.getCode());
        int bookId=Integer.valueOf(id);
        ExchangeBook eb=exchangeBookService.getExchangeBookById(bookId);
        if(eb==null)
            throw new CustomException("id为"+id+"的图书不存在",Code.NOT_FOUND_ERROR.getCode());
        Set<BookType> bookTypeSet=eb.getBookTypes();
        List<String> types=new ArrayList<>();
        if(bookTypeSet!=null && bookTypeSet.size()>0) {
            for (BookType bt : bookTypeSet)
                types.add(bt.getType());
        }
        ResMyExchangeBookInfoVO rmebiv=new ResMyExchangeBookInfoVO(eb.getId(),eb.getBookName(),eb.getAuthor(),eb.getISBN(),
                types,eb.getPress(),eb.getPublicationDate(), dateUtil.formatDate(eb.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"),
                eb.getExtra(),eb.isCanExchange());
        ResponseTemplate<ResMyExchangeBookInfoVO> responseTemplate=new ResponseTemplate<ResMyExchangeBookInfoVO>(Code.SUCCESS.getCode(),rmebiv);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchangeBook/doReleaseBook", method= RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("user")
    public ResponseEntity<ResponseTemplate> doReleaseExchangeBook(@RequestBody @Valid BookReleaseVO bookReleaseVO)throws ParseException
    {
        ExchangeBook eb=new ExchangeBook(bookReleaseVO.getBookName(),bookReleaseVO.getAuthor(),bookReleaseVO.getIsbn(),bookReleaseVO.getPress(),
                dateUtil.parse(bookReleaseVO.getPublicationDate(),"yyyy-MM-dd"),bookReleaseVO.getExtra());
        List<BookType> bookTypeList=bookTypeService.getBookTypes(bookReleaseVO.getTypes());
        eb.setBookTypes(new HashSet<>(bookTypeList));
        String email=(String)SecurityUtils.getSubject().getPrincipal();
        User user=userService.findUserByEmail(email);
        eb.setUser(user);
        Calendar calendar= Calendar.getInstance();
        eb.setReleaseDate(calendar.getTime());
        Serializable id=exchangeBookService.save(eb);
        if(id==null)
            throw new CustomException("图书发布失败",Code.DATABASE_ACCESS_ERROR.getCode());
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"图书发布成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchangeBook/doModifyBook",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> doModifyBook(@RequestBody @Valid BookModifyVO bookModifyVO)throws ParseException
    {
        ExchangeItem exchangeItem=exchangeItemService.findNotExpiredExchangeItemByBookId(bookModifyVO.getId());//确认该图书是否在交换中
        if(exchangeItem!=null)
            throw new CustomException("图书在交换中，不能修改信息",Code.ACCESS_DENIED_ERROR.getCode());
        ExchangeBook eb=exchangeBookService.getExchangeBookById(bookModifyVO.getId());
        if(eb==null)
           throw new CustomException("id为"+bookModifyVO.getId()+"的图书不存在",Code.NOT_FOUND_ERROR.getCode());
        eb.setBookName(bookModifyVO.getBookName());
        eb.setAuthor(bookModifyVO.getAuthor());
        eb.setISBN(bookModifyVO.getIsbn());
        eb.setPress(bookModifyVO.getPress());
        eb.setPublicationDate(dateUtil.parse(bookModifyVO.getPublicationDate(),"yyyy-MM-dd"));
        eb.setCanExchange(bookModifyVO.getCanExchange());
        eb.setExtra(bookModifyVO.getExtra());
        List<BookType> bookTypes=bookTypeService.getBookTypes(bookModifyVO.getTypes());
        eb.setBookTypes(new HashSet<>(bookTypes));
        exchangeBookService.update(eb);
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"图书信息修改成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchangeBook/doDeleteBook/{id}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> doDeleteBook(@PathVariable(value = "id",required = true) String id)
    {
        if(!numberUtil.isPosNum(id))
            throw new CustomException("参数校验失败",Code.METHOD_ARGUMENT_ERROR.getCode());
        int bookId=Integer.valueOf(id);
        ExchangeItem exchangeItem=exchangeItemService.findNotExpiredExchangeItemByBookId(bookId);//确认该图书是否在交换中
        if(exchangeItem!=null)
            throw new CustomException("图书在交换中，不能删除",Code.ACCESS_DENIED_ERROR.getCode());
        exchangeBookService.deleteExchangeBookById(bookId);
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"图书删除成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchangeBook/doDeleteBooks",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> doDeleteBooks(@RequestBody @Valid BooksDeleteVO booksDeleteVO)
    {
        List<Integer> bookIds=booksDeleteVO.getBookIds();
        List<ExchangeItem> exchangeItemList=exchangeItemService.findNotExpiredExchangeItemsByBookIds(bookIds);
        if(exchangeItemList!=null && exchangeItemList.size()>0)
            throw new CustomException("有图书在交换中,不能批量删除",Code.ACCESS_DENIED_ERROR.getCode());
        int count=exchangeBookService.deleteExchangeBooksByIds(bookIds);
        if(count<=0)
            throw new CustomException("批量删除图书失败",Code.DATABASE_ACCESS_ERROR.getCode());
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"删除"+count+"本图书成功");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchangeBook/getCanExchangeBook/{id}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"user,admin"},logical = Logical.OR)
    public ResponseEntity<ResponseTemplate> doGetCanExchangeBook(@PathVariable(value = "id",required = true)String id)
    {
        if(!numberUtil.isPosNum(id))
            throw new CustomException("参数校验失败",Code.METHOD_ARGUMENT_ERROR.getCode());
        int bookId=Integer.valueOf(id);
        ExchangeBook eb=exchangeBookService.findCanExchangeBookById(bookId);
        if(eb==null)
            throw new CustomException("该图书无法申请置换",Code.NOT_FOUND_ERROR.getCode());
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.SUCCESS.getCode(),"该图书可申请置换");
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate,HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchangeBook/doSearchHaveReadBooks",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("user")
    public BootstrapTableResult<ResBookReadVO> doSearchHaveReadBooks(@RequestBody @Valid BookReadVO bookReadVO){
        bookReadVO.setStatus((short)2);
        String email=(String)SecurityUtils.getSubject().getPrincipal();
        User user=new User();
        user.setEmail(email);
        long total=exchangeItemService.countExchangeItemWithWishBook(bookReadVO,user);
        List<ExchangeItem> exchangeItemList= exchangeItemService.findExchangeItemWithWishBook(bookReadVO,user);
        List<ResBookReadVO> resBookHaveReadVOList=new ArrayList<>();
        if(exchangeItemList!=null && exchangeItemList.size()>0){
            for(ExchangeItem ei:exchangeItemList){
                ExchangeBook wishBook=ei.getWishBook();
                List<String> types=new ArrayList<>();
                Set<BookType> bookTypeList=wishBook.getBookTypes();
                for(BookType bookType:bookTypeList)
                    types.add(bookType.getType());
                ResBookReadVO resBookHaveReadVO=new ResBookReadVO(wishBook.getId(),wishBook.getBookName(),wishBook.getAuthor(),
                        wishBook.getISBN(),wishBook.getPress(),dateUtil.formatDate(wishBook.getPublicationDate(),"yyyy-MM-dd"),
                        types,dateUtil.formatDate(ei.getApplicationDate(),"yyyy-MM-dd HH:mm:ss"));
                resBookHaveReadVOList.add(resBookHaveReadVO);
            }
        }
        BootstrapTableResult<ResBookReadVO> result=new BootstrapTableResult<>(total,resBookHaveReadVOList);
        return result;
    }

    @RequestMapping(value = "/exchangeBook/doSearchToReadBooks",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("user")
    public BootstrapTableResult<ResBookReadVO> doSearchToReadBooks(@RequestBody @Valid BookReadVO bookReadVO)
    {
        bookReadVO.setStatus((short)0);
        String email=(String)SecurityUtils.getSubject().getPrincipal();
        User user=new User();
        user.setEmail(email);
        long total=exchangeItemService.countExchangeItemWithWishBook(bookReadVO,user);
        List<ExchangeItem> exchangeItemList= exchangeItemService.findExchangeItemWithWishBook(bookReadVO,user);
        List<ResBookReadVO> resBookHaveReadVOList=new ArrayList<>();
        if(exchangeItemList!=null && exchangeItemList.size()>0){
            for(ExchangeItem ei:exchangeItemList){
                ExchangeBook wishBook=ei.getWishBook();
                List<String> types=new ArrayList<>();
                Set<BookType> bookTypeList=wishBook.getBookTypes();
                for(BookType bookType:bookTypeList)
                    types.add(bookType.getType());
                ResBookReadVO resBookHaveReadVO=new ResBookReadVO(wishBook.getId(),wishBook.getBookName(),wishBook.getAuthor(),
                        wishBook.getISBN(),wishBook.getPress(),dateUtil.formatDate(wishBook.getPublicationDate(),"yyyy-MM-dd"),
                        types,dateUtil.formatDate(ei.getApplicationDate(),"yyyy-MM-dd HH:mm:ss"));
                resBookHaveReadVOList.add(resBookHaveReadVO);
            }
        }
        BootstrapTableResult<ResBookReadVO> result=new BootstrapTableResult<>(total,resBookHaveReadVOList);
        return result;
    }

}
