package com.tszh.vo.requestVO;

import com.tszh.custom_validator.DateV1;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
public class ExchangeBookApplyVO {

    @NotBlank(message = "{ExchangeBookApplyVO.bookName.not.blank}")
    private String bookName1;

    @NotBlank(message = "{ExchangeBookApplyVO.bookName.not.blank}")
    private String bookName2;

    @NotBlank(message = "{ExchangeBookApplyVO.author.not.blank}")
    private String author1;

    @NotBlank(message = "{ExchangeBookApplyVO.author.not.blank}")
    private String author2;

    @NotBlank(message = "{ExchangeBookApplyVO.isbn.not.blank}")
    private String isbn1;

    @NotBlank(message = "{ExchangeBookApplyVO.isbn.not.blank}")
    private String isbn2;

    private List<String> type1;

    private List<String> type2;

    private String press1;

    private String press2;

    @DateV1(message = "{ExchangeBookApplyVO.publicationDate.illegal.format}")
    private String publicationDate1;

    @DateV1(message = "{ExchangeBookApplyVO.publicationDate.illegal.format}")
    private String publicationDate2;

    private String extra1;

    private String extra2;

    @NotBlank(message = "{ExchangeBookApplyVO.owner.not.blank}")
    private String owner2;

    public String getBookName1() {
        return bookName1;
    }

    public void setBookName1(String bookName1) {
        this.bookName1 = bookName1;
    }

    public String getBookName2() {
        return bookName2;
    }

    public void setBookName2(String bookName2) {
        this.bookName2 = bookName2;
    }

    public String getAuthor1() {
        return author1;
    }

    public void setAuthor1(String author1) {
        this.author1 = author1;
    }

    public String getAuthor2() {
        return author2;
    }

    public void setAuthor2(String author2) {
        this.author2 = author2;
    }

    public String getIsbn1() {
        return isbn1;
    }

    public void setIsbn1(String isbn1) {
        this.isbn1 = isbn1;
    }

    public String getIsbn2() {
        return isbn2;
    }

    public void setIsbn2(String isbn2) {
        this.isbn2 = isbn2;
    }

    public List<String> getType1() {
        return type1;
    }

    public void setType1(List<String> type1) {
        this.type1 = type1;
    }

    public List<String> getType2() {
        return type2;
    }

    public void setType2(List<String> type2) {
        this.type2 = type2;
    }

    public String getPress1() {
        return press1;
    }

    public void setPress1(String press1) {
        this.press1 = press1;
    }

    public String getPress2() {
        return press2;
    }

    public void setPress2(String press2) {
        this.press2 = press2;
    }

    public String getPublicationDate1() {
        return publicationDate1;
    }

    public void setPublicationDate1(String publicationDate1) {
        this.publicationDate1 = publicationDate1;
    }

    public String getPublicationDate2() {
        return publicationDate2;
    }

    public void setPublicationDate2(String publicationDate2) {
        this.publicationDate2 = publicationDate2;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public String getOwner2() {
        return owner2;
    }

    public void setOwner2(String owner2) {
        this.owner2 = owner2;
    }
}
