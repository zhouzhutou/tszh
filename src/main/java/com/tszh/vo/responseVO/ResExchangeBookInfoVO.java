package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/18 0018.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResExchangeBookInfoVO {

    private int id;

    private String bookName;

    private String author;

    private String isbn;

    private String type;

    private String press;

    private Date publicationDate;

    private String releaseDate;

    private String owner;

    private String extra;

    private boolean canExchange;

    public ResExchangeBookInfoVO() {
    }


    public ResExchangeBookInfoVO(int id, String bookName, String author, String isbn, String type, String press,
                                 Date publicationDate, String owner, String extra, boolean canExchange,String releaseDate) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.isbn = isbn;
        this.type = type;
        this.press = press;
        this.publicationDate = publicationDate;
        this.owner = owner;
        this.extra = extra;
        this.canExchange = canExchange;
        this.releaseDate=releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public boolean isCanExchange() {
        return canExchange;
    }

    public void setCanExchange(boolean canExchange) {
        this.canExchange = canExchange;
    }
}
