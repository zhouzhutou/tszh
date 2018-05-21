package com.tszh.vo.requestVO;

import com.tszh.custom_validator.DateV1;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
public class ExchangeBookItemSearchVO {

    @NotNull(message = "{ExchangeBookItemSearchVO.pageSize.not.null}")
    @Min(value = 1,message = "{ExchangeBookItemSearchVO.pageSize.min.illegal}")
    private Integer pageSize;

    @NotNull(message = "{ExchangeBookItemSearchVO.pageNumber.not.null}")
    @Min(value = 1,message = "{ExchangeBookItemSearchVO.pageNumber.min.illegal}")
    private Integer pageNumber;

    //申请号
    private String id;

    //拥有图书
    private String bookName1;

    //申请图书
    private String bookName2;

    //申请日期
    @DateV1(message = "{ExchangeBookItemSearchVO.applicationDate.illegal.format}")
    private String applicationDate;

    //状态
    //@NotNull(message = "{ExchangeBookItemSearchVO.status.not.null}")
    @Min(value = 0,message = "{ExchangeBookItemSearchVO.status.min.illegal}")
    @Max(value = 2,message = "{ExchangeBookItemSearchVO.status.max.illegal}")
    private Short status;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName1() {
        return bookName1;
    }

    public void setBookName1(String bookName1) {
        this.bookName1 = bookName1;
    }

    public String getBookName2() {
        return bookName2;
    }

    public void setBookName2(String bookName2) {
        this.bookName2 = bookName2;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ExchangeBookItemSearchVO{" +
                "pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ", id='" + id + '\'' +
                ", bookName1='" + bookName1 + '\'' +
                ", bookName2='" + bookName2 + '\'' +
                ", applicationDate='" + applicationDate + '\'' +
                ", status=" + status +
                '}';
    }
}
