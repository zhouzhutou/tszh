package com.tszh.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/4/11 0011.
 */
@Entity
@Table(name = "t_helloworld")
public class HelloWorld {

    @Id
    @GenericGenerator(name = "gen_identity",strategy = "identity")
    @GeneratedValue(generator = "gen_identity")
    private Integer id;

    @Column
    private String name;

    public void setName(String name)
    {
        this.name=name;
    }

    public String getName()
    {
        return this.name;
    }


}
