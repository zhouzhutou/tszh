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
@Table(name = "t_address")
public class Address {

    @Id
    @Column(name = "address_id")
    @GenericGenerator(name="gen_identity",strategy = "identity")
    @GeneratedValue(generator = "gen_identity")
    private int id;

    @Column(length = 32)
    private String province;

    @Column(length = 32)
    private String city;

    @Column(length = 32)
    private String county;

    @Column(length = 64)
    private String streetAddress;

    @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",unique = true)
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @Column
    private boolean deleted=false;


    public Address() {
    }

    public Address(String province, String city, String county, String streetAddress) {
        this.province = province;
        this.city = city;
        this.county = county;
        this.streetAddress = streetAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public User getUser() {
        return user;
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

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", user=" + user +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", deleted=" + deleted +
                '}';
    }
}
