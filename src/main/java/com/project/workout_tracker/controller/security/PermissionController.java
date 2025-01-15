package com.project.workout_tracker.controller.security;

import com.project.workout_tracker.model.security.Permission;
import com.project.workout_tracker.service.security.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions () {
        List<Permission> permissionList = permissionService.findAll();
        return new ResponseEntity<>(permissionList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById (@PathVariable Long id) {
        Optional<Permission> permission = permissionService.findById(id);
        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Permission> createPermission (@RequestBody Permission permission) {
        Optional<Permission> newPermission = permissionService.save(permission);
        return newPermission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
