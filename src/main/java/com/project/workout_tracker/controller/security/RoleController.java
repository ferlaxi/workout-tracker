package com.project.workout_tracker.controller.security;

import com.project.workout_tracker.model.security.Permission;
import com.project.workout_tracker.model.security.Role;
import com.project.workout_tracker.service.security.IPermissionService;
import com.project.workout_tracker.service.security.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles () {
        List<Role> roleList = roleService.findAll();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById (@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Role> createRole (@RequestBody Role role) {
        Set<Permission> permissionsList = new HashSet<>();
        Permission foundPermission;

        for (Permission permission : role.getPermissionsList()) {
            foundPermission = permissionService.findById(permission.getId()).orElse(null);
            if (foundPermission != null) {
                permissionsList.add(foundPermission);
            }
        }

        role.setPermissionsList(permissionsList);
        Optional<Role> newRole = roleService.save(role);
        return newRole.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
