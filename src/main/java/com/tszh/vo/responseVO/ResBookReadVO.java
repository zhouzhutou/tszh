package com.tszh.vo.responseVO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29 0029.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResBookReadVO {

    private int id;

    private String bookName;

    private String author;

    private String isbn;

    private String press;

    private String publicationDate;

    private List<String> types;

    private String applicationDate;

    public ResBookReadVO(){

    }

    public ResBookReadVO(int id, String bookName, String author, String isbn, String press, String publicationDate, List<String> types, String applicationDate) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.isbn = isbn;
        this.press = press;
        this.publicationDate = publicationDate;
        this.types = types;
        this.applicationDate = applicationDate;
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

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
}
