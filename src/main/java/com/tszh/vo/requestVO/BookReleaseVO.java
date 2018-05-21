package com.tszh.vo.requestVO;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 2018/5/21 0021.
 */
public class BookReleaseVO {

    @NotBlank(message = "{BookReleaseVO.bookName.not.blank}")
    private String bookName;

    @NotBlank(message = "{BookReleaseVO.author.not.blank}")
    private String author;

    @NotBlank(message = "{BookReleaseVO.isbn.not.blank}")
    private String isbn;

    private List<String> types;

    private String press;

    private String publicationDate;

    @NotNull(message="{BookReleaseVO.canExchange.not.null}")
    private Boolean canExchange;

    private String extra;

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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean isCanExchange() {
        return canExchange;
    }

    public void setCanExchange(boolean canExchange) {
        this.canExchange = canExchange;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
