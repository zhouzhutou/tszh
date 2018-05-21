package com.tszh.vo.requestVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
public class BookSearchVO {

    @NotNull(message = "{BookSearchVO.pageSize.not.null}")
    @Min(value = 1,message = "{BookSearchVO.pageSize.min.illegal}")
    private Integer pageSize;

    @NotNull(message = "{BookSearchVO.pageNumber.not.null}")
    @Min(value = 1,message = "{BookSearchVO.pageNumber.min.illegal}")
    private Integer pageNumber;

    private String bookName;

    private String author;

    private String press;

    private String isbn;

    private String bookType;

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

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    @Override
    public String toString() {
        return "BookSearchVO{" +
                "pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", isbn='" + isbn + '\'' +
                ", bookType='" + bookType + '\'' +
                '}';
    }
}
