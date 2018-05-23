package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/22 0022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResMyExchangeBookInfoVO {

    private int id;

    private String bookName;

    private String author;

    private String isbn;

    private List<String> types;

    private String press;

    private Date publicationDate;

    private String releaseDate;

    private String extra;

    private boolean canExchange;

    public ResMyExchangeBookInfoVO(){}

    public ResMyExchangeBookInfoVO(int id, String bookName, String author, String isbn, List<String> types, String press,
                                   Date publicationDate, String releaseDate, String extra, boolean canExchange) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.isbn = isbn;
        this.types = types;
        this.press = press;
        this.publicationDate = publicationDate;
        this.releaseDate = releaseDate;
        this.extra = extra;
        this.canExchange = canExchange;
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

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
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
