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
    @JoinColumn(name="my_own_book_id",referencedColumnName = "exchange_book_id")
    private ExchangeBook myOwnBook;

    @ManyToOne(targetEntity = ExchangeBook.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="wish_book_id",referencedColumnName = "exchange_book_id")
    private ExchangeBook wishBook;

    @ManyToOne(targetEntity = User.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="apply_user_id",referencedColumnName = "user_id")
    private User applyUser;

   /* @OneToOne(targetEntity = WishBook.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_book_id",referencedColumnName = "wish_book_id",unique = true)
    private WishBook wishBook;*/

    /*@ManyToOne(targetEntity = User.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name="own_user_id",referencedColumnName = "user_id")
    private User owner;*/
    @ManyToOne(targetEntity = User.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch =FetchType.LAZY)
    @JoinColumn(name="wish_book_owner_id",referencedColumnName = "user_id")
    private User wishBookOwner;

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

    public ExchangeItem(ExchangeBook myOwnBook, User applyUser, ExchangeBook wishBook, User wishBookOwner, short status, Date applicationDate) {
        this.myOwnBook = myOwnBook;
        this.applyUser = applyUser;
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

    public ExchangeBook getMyOwnBook() {
        return myOwnBook;
    }

    public void setMyOwnBook(ExchangeBook myOwnBook) {
        this.myOwnBook = myOwnBook;
    }

    public ExchangeBook getWishBook() {
        return wishBook;
    }

    public void setWishBook(ExchangeBook wishBook) {
        this.wishBook = wishBook;
    }

    public User getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(User applyUser) {
        this.applyUser = applyUser;
    }

    public User getWishBookOwner() {
        return wishBookOwner;
    }

    public void setWishBookOwner(User wishBookOwner) {
        this.wishBookOwner = wishBookOwner;
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
