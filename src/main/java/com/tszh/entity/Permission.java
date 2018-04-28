package com.tszh.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
@Entity
@Table(name = "t_permission")
public class Permission {

    @Id
    @Column(name = "permission_id")
    @GenericGenerator(name="gen_identity",strategy = "identity")
    @GeneratedValue(generator = "gen_identity")
    private int id;

    @Column(length = 64)
    private String permissionName;

    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_permission",
            joinColumns = @JoinColumn(name = "permission_id",referencedColumnName = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"))
    private Set<Role> roles;

    @Column
    private boolean deleted=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
