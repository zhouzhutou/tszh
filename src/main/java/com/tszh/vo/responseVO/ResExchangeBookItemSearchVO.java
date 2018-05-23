package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/19 0019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResExchangeBookItemSearchVO {

    private String id;

    private String bookName1;

    private String bookName2;

    private String owner1;

    private String owner2;

    private String applicationDate;

    private String passedDate;

    private String  expiredDate;

    private short status;

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

    public String getOwner1() {
        return owner1;
    }

    public void setOwner1(String owner1) {
        this.owner1 = owner1;
    }

    public String getBookName2() {
        return bookName2;
    }

    public void setBookName2(String bookName2) {
        this.bookName2 = bookName2;
    }

    public String getOwner2() {
        return owner2;
    }

    public void setOwner2(String owner2) {
        this.owner2 = owner2;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getPassedDate() {
        return passedDate;
    }

    public void setPassedDate(String passedDate) {
        this.passedDate = passedDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
