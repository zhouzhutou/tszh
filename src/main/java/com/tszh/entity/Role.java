package com.tszh.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @Column(name = "role_id")
    @GenericGenerator(name="gen_identity",strategy = "identity")
    @GeneratedValue(generator = "gen_identity")
    private int id;

    @Column(length = 64)
    private String roleName;

    @OneToMany(targetEntity = User.class,mappedBy = "role")
    private Set<User> users;

    @ManyToMany(targetEntity = Permission.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_permission",
            joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id",referencedColumnName = "permission_id"))
    private Set<Permission> permissions;

    @Column
    private boolean deleted=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
