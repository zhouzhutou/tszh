package com.tszh.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ExchangeBook
 * Created by Administrator on 2018/4/17 0017.
 */
@Entity
@Table(name = "t_exchange_book")
public class ExchangeBook {

    @Id
    @Column(name = "exchange_book_id")
    @GenericGenerator(name="gen_identity",strategy = "identity")
    @GeneratedValue(generator = "gen_identity")
    private int id;

    /**
     * 书名
     */
    @Column(length = 128)
    private String bookName;

    /**
     * 作者
     */
    @Column(length = 64)
    private String author;

    /**
     * ISBN号
     */
    @Column
    private String ISBN;

    /**
     * 出版社
     */
    @Column(length = 128)
    private String press;

    /**
     * 发布日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    /**
     * 出版日期
     */
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Column
    private String extra;

    @Column
    private boolean inLibrary=true;

    @Column
    private boolean canExchange=true;

    @ManyToOne(targetEntity = User.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    @ManyToMany(targetEntity = BookType.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "t_book_type",
            joinColumns = @JoinColumn(name = "exchange_book_id",referencedColumnName = "exchange_book_id"),
            inverseJoinColumns = @JoinColumn(name = "booktype_id",referencedColumnName = "booktype_id"))
    private Set<BookType> bookTypes=new HashSet<>();

    @OneToMany(targetEntity = ExchangeItem.class,fetch = FetchType.LAZY)
    private Set<ExchangeItem> exchangeItems;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @Column
    private boolean deleted=false;

    public ExchangeBook() {
    }

    public ExchangeBook(String bookName, String author, String ISBN, String press,Date publicationDate, String extra) {
        this.bookName = bookName;
        this.author = author;
        this.ISBN = ISBN;
        this.press = press;
        this.publicationDate = publicationDate;
        this.extra = extra;
    }



    public Set<ExchangeItem> getExchangeItems() {
        return exchangeItems;
    }

    public void setExchangeItems(Set<ExchangeItem> exchangeItems) {
        this.exchangeItems = exchangeItems;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public boolean isInLibrary() {
        return inLibrary;
    }

    public void setInLibrary(boolean inLibrary) {
        this.inLibrary = inLibrary;
    }

    public boolean isCanExchange() {
        return canExchange;
    }

    public void setCanExchange(boolean canExchange) {
        this.canExchange = canExchange;
    }

    public User getUser() {
        return user;
    }

    public Set<BookType> getBookTypes() {
        return bookTypes;
    }

    public void setBookTypes(Set<BookType> bookTypes) {
        this.bookTypes = bookTypes;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
