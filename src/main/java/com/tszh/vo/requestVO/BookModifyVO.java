package com.tszh.vo.requestVO;

import com.tszh.custom_validator.DateV1;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23 0023.
 */
public class BookModifyVO {

    @NotNull(message = "{BookModifyVO.id.not.null}")
    private Integer id;

    @NotBlank(message = "{BookModifyVO.bookName.not.blank}")
    @Length(max = 128,message = "{BookModifyVO.bookName.max.length}")
    private String bookName;

    @NotBlank(message = "{BookModifyVO.author.not.blank}")
    @Length(max = 128,message = "{BookModifyVO.author.max.length}")
    private String author;

    @NotBlank(message = "{BookModifyVO.isbn.not.blank}")
    @Length(max = 128,message = "{BookModifyVO.isbn.max.length}")
    private String isbn;

    private List<String> types;

    @Length(max = 128,message = "{BookModifyVO.press.max.length}")
    private String press;

    @Length(max = 64,message = "{BookModifyVO.publicationDate.max.length}")
    @DateV1(message = "{BookModifyVO.applicationDate.illegal.format}")
    private String publicationDate;

    @NotNull(message="{BookModifyVO.canExchange.not.null}")
    private Boolean canExchange;

    @Length(max = 512,message = "{BookModifyVO.extra.max.length}")
    private String extra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
