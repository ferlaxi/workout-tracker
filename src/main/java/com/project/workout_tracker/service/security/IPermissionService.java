package com.project.workout_tracker.service.security;

import com.project.workout_tracker.model.security.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {
    public List<Permission> findAll();
    public Optional<Permission> findById (Long id);
    public Optional<Permission> save (Permission permission);
    public void deleteById (Long id);
    public Optional<Permission> update (Long id, Permission permission);
}
