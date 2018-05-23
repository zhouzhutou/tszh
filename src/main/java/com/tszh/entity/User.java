package com.tszh.entity;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User
 * Created by Administrator on 2018/4/17 0017.
 */
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GenericGenerator(name="gen_identity",strategy = "identity")
    @GeneratedValue(generator = "gen_identity")
    private int id;

    @Column(length = 64)
    private String username;

    @Column(length = 64)
    private String email;

    @Column
    private String salt;

    @Column
    private String password;

    @Column
    private byte sex;//0-男，1-女

    @OneToOne(targetEntity = Address.class,mappedBy = "user",fetch = FetchType.LAZY)
    private Address address;

    @Column
    private int deposit;

    @OneToMany(targetEntity = ExchangeBook.class,mappedBy = "user",fetch = FetchType.LAZY)
    Set<ExchangeBook> exchangeBooks=new HashSet<>();

/*    @OneToMany(targetEntity = WishBook.class,mappedBy = "user",fetch = FetchType.LAZY)
    Set<WishBook> wishBooks=new HashSet<>();*/

    @OneToMany(targetEntity = ExchangeItem.class,mappedBy = "user",fetch = FetchType.LAZY)
    Set<ExchangeItem> exchangeItems;

    @ManyToOne(targetEntity = Role.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",referencedColumnName = "role_id",nullable = false)
    private Role role;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @Column
    private boolean deleted=false;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public Set<ExchangeBook> getExchangeBooks() {
        return exchangeBooks;
    }

    public void setExchangeBooks(Set<ExchangeBook> exchangeBooks) {
        this.exchangeBooks = exchangeBooks;
    }

    public Set<ExchangeItem> getExchangeItems() {
        return exchangeItems;
    }

    public void setExchangeItems(Set<ExchangeItem> exchangeItems) {
        this.exchangeItems = exchangeItems;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", address=" + address +
                ", deposit=" + deposit +
                ", exchangeBooks=" + exchangeBooks +
                ", exchangeItems=" + exchangeItems +
                ", role=" + role +
                ", lastLoginDate=" + lastLoginDate +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", deleted=" + deleted +
                '}';
    }
}
