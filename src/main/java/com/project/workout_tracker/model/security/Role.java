package com.project.workout_tracker.model.security;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "id_role"), inverseJoinColumns = @JoinColumn(name = "id_permission"))
    Set<Permission> permissionsList = new HashSet<>();

    public Role() {
    }

    public Role(Long id, String role, Set<Permission> permissionsList) {
        this.id = id;
        this.role = role;
        this.permissionsList = permissionsList;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public Set<Permission> getPermissionsList() {
        return permissionsList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPermissionsList(Set<Permission> permissionsList) {
        this.permissionsList = permissionsList;
    }
}
