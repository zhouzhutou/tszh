package com.tszh.vo.requestVO;

import com.tszh.custom_validator.DateV1;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
public class ExchangeBookApplyVO {

    private Integer myOwnBookId;

    @NotNull(message = "{ExchangeBookApplyVO.wishBookId.not.null}")
    private Integer wishBookId;

    @NotBlank(message = "{ExchangeBookApplyVO.bookName.not.blank}")
    @Length(max = 128,message = "{ExchangeBookApplyVO.bookName.max.length}")
    private String bookName;

    @NotBlank(message = "{ExchangeBookApplyVO.author.not.blank}")
    @Length(max = 128,message = "{ExchangeBookApplyVO.author.max.length}")
    private String author;

    @NotBlank(message = "{ExchangeBookApplyVO.isbn.not.blank}")
    @Length(max = 128,message = "{ExchangeBookApplyVO.isbn.max.length}")
    private String isbn;

    private List<String> types;

    @Length(max = 128,message = "{ExchangeBookApplyVO.press.max.length}")
    private String press;

    @Length(max = 64,message = "{ExchangeBookApplyVO.publicationDate.max.length}")
    @DateV1(message = "{ExchangeBookApplyVO.applicationDate.illegal.format}")
    private String publicationDate;

    @NotNull(message="{ExchangeBookApplyVO.canExchange.not.null}")
    private Boolean canExchange;

    @Length(max = 512,message = "{ExchangeBookApplyVO.extra.max.length}")
    private String extra;

    public Integer getMyOwnBookId() {
        return myOwnBookId;
    }

    public void setMyOwnBookId(Integer myOwnBookId) {
        this.myOwnBookId = myOwnBookId;
    }

    public Integer getWishBookId() {
        return wishBookId;
    }

    public void setWishBookId(Integer wishBookId) {
        this.wishBookId = wishBookId;
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
