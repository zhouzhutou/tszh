package com.tszh.controller_rest;

import com.tszh.cons.Code;
import com.tszh.entity.BookType;
import com.tszh.entity.ExchangeBook;
import com.tszh.entity.User;
import com.tszh.exception.CustomException;
import com.tszh.service.ExchangeBookService;
import com.tszh.util.DateUtil;
import com.tszh.util.NumberUtil;
import com.tszh.vo.BootstrapTableResult;
import com.tszh.vo.ResponseTemplate;
import com.tszh.vo.requestVO.BookReleaseVO;
import com.tszh.vo.requestVO.BookSearchVO;
import com.tszh.vo.responseVO.ResBookSearchVO;
import com.tszh.vo.responseVO.ResExchangeBookInfoVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@Controller
@RequestMapping("/")
public class RestExchangeBookController {

    @Autowired
    ExchangeBookService exchangeBookService;

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
    public BootstrapTableResult<ResBookSearchVO> doSearchBook(@RequestBody BookSearchVO bookSearchVO)
    {
        //System.out.println(bookSearchVO);
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
                rbsv.setPublicationDate(eb.getPublicationDate());
                rbsv.setReleaseDate(dateUtil.formatDate(eb.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
                rbsv.setCanExchange(eb.isCanExchange());
                String type="";
                Set<BookType> bookTypeSet=eb.getBookTypes();
                Iterator<BookType> iter=bookTypeSet.iterator();
                int c=0;
                while (iter.hasNext()){
                    if(c>0)
                        type+=","+iter.next().getType();
                    else
                        type+=iter.next().getType();
                    c++;
                }
                rbsv.setType(type);
                resBookSearchVOList.add(rbsv);
            }
        }
        BootstrapTableResult<ResBookSearchVO> result=new BootstrapTableResult<>(total,resBookSearchVOList);
        return result;
    }

    @RequestMapping(value = "/exchangeBook/doMySearchBook",method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableResult<ResBookSearchVO> doMySearchBook(@RequestBody BookSearchVO bookSearchVO)
    {
        //System.out.println(bookSearchVO);
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
                rbsv.setPublicationDate(eb.getPublicationDate());
                rbsv.setReleaseDate(dateUtil.formatDate(eb.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
                rbsv.setCanExchange(eb.isCanExchange());
                String type="";
                Set<BookType> bookTypeSet=eb.getBookTypes();
                Iterator<BookType> iter=bookTypeSet.iterator();
                int c=0;
                while (iter.hasNext()){
                    if(c>0)
                        type+=","+iter.next().getType();
                    else
                        type+=iter.next().getType();
                    c++;
                }
                rbsv.setType(type);
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
    public ResponseEntity<ResponseTemplate> getSearchBookById(@PathVariable("id") String id)
    {
        if(!numberUtil.isPosNum(id))
            throw new CustomException("参数校验失败",Code.METHOD_ARGUMENT_ERROR.getCode());
        int bookId=Integer.valueOf(id);
        ExchangeBook eb=exchangeBookService.getExchangeBookById(bookId);
        if(eb==null)
            throw new CustomException("id为"+id+"的图书不存在",Code.NOT_FOUND_ERROR.getCode());
        Set<BookType> bookTypes=eb.getBookTypes();
        String type="";
        Iterator<BookType> iter=bookTypes.iterator();
        int c=0;
        while (iter.hasNext()){
            if(c>0)
                type+=","+iter.next().getType();
            else
                type+=iter.next().getType();
            c++;
        }
        ResExchangeBookInfoVO rebiv=new ResExchangeBookInfoVO(eb.getId(),eb.getBookName(),eb.getAuthor(),eb.getISBN(),
                type,eb.getPress(),eb.getPublicationDate(),eb.getUser().getUsername(),eb.getExtra(),eb.isCanExchange(),
                dateUtil.formatDate(eb.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
        ResponseTemplate<ResExchangeBookInfoVO> responseTemplate=new ResponseTemplate<ResExchangeBookInfoVO>(Code.SUCCESS.getCode(),rebiv);
        ResponseEntity<ResponseTemplate> responseEntity=new ResponseEntity<ResponseTemplate>(responseTemplate, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/exchangeBook/doReleaseBook", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseTemplate> doReleaseExchangeBook(@RequestBody @Valid BookReleaseVO bookReleaseVO)
    {
        return null;
    }

}
