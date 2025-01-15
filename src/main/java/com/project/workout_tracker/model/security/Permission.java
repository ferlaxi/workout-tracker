package com.project.workout_tracker.model.security;

import jakarta.persistence.*;

@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String permission;

    public Permission() {
    }

    public Permission(Long id, String permission) {
        this.id = id;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
