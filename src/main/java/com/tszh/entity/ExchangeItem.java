package com.tszh.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/17 0017.
 */
@Entity
@Table(name = "t_exchange_item")
public class ExchangeItem {

    @Id
    @Column(name = "exchange_item_id")
    @GenericGenerator(name="gen_identity",strategy = "uuid")
    @GeneratedValue(generator = "gen_identity")
    private String id;

    @ManyToOne(targetEntity = ExchangeBook.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="exchange_book_id",referencedColumnName = "exchange_book_id")
    private ExchangeBook exchangeBook;

    @ManyToOne(targetEntity = User.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="apply_user_id",referencedColumnName = "user_id")
    private User user;

    @OneToOne(targetEntity = WishBook.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_book_id",referencedColumnName = "wish_book_id",unique = true)
    private WishBook wishBook;

    /*@ManyToOne(targetEntity = User.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name="own_user_id",referencedColumnName = "user_id")
    private User owner;*/
    @Column(length = 64)
    private String wishBookOwner;

    @Column
    private short status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date passedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date  expiredDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @Column
    private boolean deleted=false;

    public ExchangeItem() {
    }

    public ExchangeItem(User user, ExchangeBook exchangeBook, WishBook wishBook, String wishBookOwner, short status, Date applicationDate) {
        this.exchangeBook = exchangeBook;
        this.user = user;
        this.wishBook = wishBook;
        this.wishBookOwner = wishBookOwner;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getPassedDate() {
        return passedDate;
    }

    public void setPassedDate(Date passedDate) {
        this.passedDate = passedDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public ExchangeBook getExchangeBook() {
        return exchangeBook;
    }

    public void setExchangeBook(ExchangeBook exchangeBook) {
        this.exchangeBook = exchangeBook;
    }

    public WishBook getWishBook() {
        return wishBook;
    }

    public void setWishBook(WishBook wishBook) {
        this.wishBook = wishBook;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWishBookOwner() {
        return wishBookOwner;
    }

    public void setWishBookOwner(String wishBookOwner) {
        this.wishBookOwner = wishBookOwner;
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
