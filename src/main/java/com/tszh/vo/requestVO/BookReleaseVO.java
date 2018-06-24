package com.tszh.vo.requestVO;

import com.tszh.custom_validator.DateV1;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 2018/5/21 0021.
 */
public class BookReleaseVO {

    @NotBlank(message = "{BookReleaseVO.bookName.not.blank}")
    @Length(max = 128,message = "{BookReleaseVO.bookName.max.length}")
    private String bookName;

    @NotBlank(message = "{BookReleaseVO.author.not.blank}")
    @Length(max = 128,message = "{BookReleaseVO.author.max.length}")
    private String author;

    @NotBlank(message = "{BookReleaseVO.isbn.not.blank}")
    @Length(max = 128,message = "{BookReleaseVO.isbn.max.length}")
    private String isbn;

    private List<String> types;

    @Length(max = 128,message = "{BookReleaseVO.press.max.length}")
    private String press;

    @Length(max = 64,message = "{BookReleaseVO.publicationDate.max.length}")
    @DateV1(message = "{BookReleaseVO.applicationDate.illegal.format}")
    private String publicationDate;

    @NotNull(message="{BookReleaseVO.canExchange.not.null}")
    private Boolean canExchange;

    @Length(max = 512,message = "{BookReleaseVO.extra.max.length}")
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

    public Boolean getCanExchange() {
        return canExchange;
    }

    public void setCanExchange(Boolean canExchange) {
        this.canExchange = canExchange;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
