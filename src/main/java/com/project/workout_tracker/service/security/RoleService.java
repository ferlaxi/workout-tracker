package com.project.workout_tracker.service.security;

import com.project.workout_tracker.model.security.Role;
import com.project.workout_tracker.repository.security.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService{

    @Autowired
    private IRoleRepository iRoleRepository;

    @Override
    public List<Role> findAll() {
        return iRoleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return iRoleRepository.findById(id);
    }

    @Override
    public Optional<Role> save(Role role) {
        Role newRole = iRoleRepository.save(role);
        return Optional.of(newRole);
    }

    @Override
    public void deleteById(Long id) {
        iRoleRepository.deleteById(id);
    }

    @Override
    public Optional<Role> update(Long id, Role Role) {
        return Optional.empty();
    }
}
