package com.project.workout_tracker.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserSec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean accountNotExpired;
    private Boolean accountNotLocked;
    private Boolean credentialNotExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> rolesList = new HashSet<>();

    public UserSec() {
    }

    public UserSec(Long id, String username, String password, Boolean enabled, Boolean accountNotExpired, Boolean accountNotLocked, Boolean credentialNotExpired, Set<Role> rolesList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNotExpired = accountNotExpired;
        this.accountNotLocked = accountNotLocked;
        this.credentialNotExpired = credentialNotExpired;
        this.rolesList = rolesList;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getAccountNotExpired() {
        return accountNotExpired;
    }

    public Boolean getAccountNotLocked() {
        return accountNotLocked;
    }

    public Boolean getCredentialNotExpired() {
        return credentialNotExpired;
    }

    public Set<Role> getRolesList() {
        return rolesList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNotExpired(Boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public void setAccountNotLocked(Boolean accountNotLocked) {
        this.accountNotLocked = accountNotLocked;
    }

    public void setCredentialNotExpired(Boolean credentialNotExpired) {
        this.credentialNotExpired = credentialNotExpired;
    }

    public void setRolesList(Set<Role> rolesList) {
        this.rolesList = rolesList;
    }
}
