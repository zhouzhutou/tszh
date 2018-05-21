package com.tszh.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@Entity
@Table(name = "t_booktype")
public class BookType {

    @Id
    @Column(name = "booktype_id")
    @GenericGenerator(name="gen_identity",strategy = "identity")
    @GeneratedValue(generator = "gen_identity")
    private int id;

    @Column
    private String type;

    @ManyToMany(targetEntity = ExchangeBook.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "t_book_type",
            joinColumns = @JoinColumn(name = "booktype_id",referencedColumnName = "booktype_id"),
            inverseJoinColumns = @JoinColumn(name = "exchange_book_id",referencedColumnName = "exchange_book_id"))
    private Set<ExchangeBook> exchangeBooks=new HashSet<>();

    @ManyToMany(targetEntity = WishBook.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "t_wish_book_type",
            joinColumns = @JoinColumn(name = "booktype_id",referencedColumnName = "booktype_id"),
            inverseJoinColumns = @JoinColumn(name = "wish_book_id",referencedColumnName = "wish_book_id"))
    private Set<WishBook> wishBooks=new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ExchangeBook> getExchangeBooks() {
        return exchangeBooks;
    }

    public void setExchangeBooks(Set<ExchangeBook> exchangeBooks) {
        this.exchangeBooks = exchangeBooks;
    }

    public Set<WishBook> getWishBooks() {
        return wishBooks;
    }

    public void setWishBooks(Set<WishBook> wishBooks) {
        this.wishBooks = wishBooks;
    }
}
