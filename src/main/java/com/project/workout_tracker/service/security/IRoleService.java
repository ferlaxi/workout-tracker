package com.project.workout_tracker.service.security;

import com.project.workout_tracker.model.security.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    public List<Role> findAll();
    public Optional<Role> findById (Long id);
    public Optional<Role> save (Role role);
    public void deleteById (Long id);
    public Optional<Role> update (Long id, Role Role);
}
