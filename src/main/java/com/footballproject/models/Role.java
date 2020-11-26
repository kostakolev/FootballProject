package com.footballproject.models;

import com.footballproject.models.enums.RoleType;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Role {

    @Id
    @Column(name = "auth_id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}


