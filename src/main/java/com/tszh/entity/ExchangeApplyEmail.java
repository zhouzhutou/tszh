package com.tszh.entity;

import java.util.Date;

/**
 * Created by Administrator on 2018/6/21 0021.
 */
public class ExchangeApplyEmail extends Email{

    private String exchangeItemId;

    private String applyUserName;

    private String myOwnBookName;

    private String wishBookOwnerName;

    private String wishBookName;

    private Date expireDate;

    private String mailingAddress;

    public ExchangeApplyEmail(){

    }

    public ExchangeApplyEmail(String fromAddress, String toAddress, String subject, String contentPath, Date sendDate,String exchangeItemId,
                              String applyUserName, String myOwnBookName, String wishBookOwnerName, String wishBookName,
                              Date expireDate, String mailingAddress) {
        super(fromAddress, toAddress, subject, contentPath, sendDate);
        this.exchangeItemId=exchangeItemId;
        this.applyUserName = applyUserName;
        this.myOwnBookName = myOwnBookName;
        this.wishBookOwnerName = wishBookOwnerName;
        this.wishBookName = wishBookName;
        this.expireDate = expireDate;
        this.mailingAddress = mailingAddress;
    }

    public ExchangeApplyEmail(String fromAddress, String toAddress, String subject, String contentPath, Date sendDate, String exchangeItemId,
                              String applyUserName, String myOwnBookName, String wishBookOwnerName, String wishBookName) {
        super(fromAddress, toAddress, subject, contentPath, sendDate);
        this.exchangeItemId = exchangeItemId;
        this.applyUserName = applyUserName;
        this.myOwnBookName = myOwnBookName;
        this.wishBookOwnerName = wishBookOwnerName;
        this.wishBookName = wishBookName;
    }

    public String getExchangeItemId() {
        return exchangeItemId;
    }

    public void setExchangeItemId(String exchangeItemId) {
        this.exchangeItemId = exchangeItemId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getMyOwnBookName() {
        return myOwnBookName;
    }

    public void setMyOwnBookName(String myOwnBookName) {
        this.myOwnBookName = myOwnBookName;
    }

    public String getWishBookOwnerName() {
        return wishBookOwnerName;
    }

    public void setWishBookOwnerName(String wishBookOwnerName) {
        this.wishBookOwnerName = wishBookOwnerName;
    }

    public String getWishBookName() {
        return wishBookName;
    }

    public void setWishBookName(String wishBookName) {
        this.wishBookName = wishBookName;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}
