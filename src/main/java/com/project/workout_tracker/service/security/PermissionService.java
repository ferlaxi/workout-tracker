package com.project.workout_tracker.service.security;

import com.project.workout_tracker.model.security.Permission;
import com.project.workout_tracker.repository.security.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService{

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Optional<Permission> save(Permission permission) {
        Permission newPermission = permissionRepository.save(permission);
        return Optional.of(newPermission);
    }

    @Override
    public void deleteById(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Optional<Permission> update(Long id, Permission permission) {
        return Optional.empty();
    }
}
