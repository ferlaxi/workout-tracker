package com.project.workout_tracker.controller.security;

import com.project.workout_tracker.model.security.Role;
import com.project.workout_tracker.model.security.UserSec;
import com.project.workout_tracker.service.security.IRoleService;
import com.project.workout_tracker.service.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserSec>> getAllUsers () {
        List<UserSec> userSecList = userService.findAll();
        return new ResponseEntity<>(userSecList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSec> getUserById (@PathVariable Long id) {
        Optional<UserSec> userSec = userService.findById(id);
        return userSec.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserSec> createUser (UserSec userSec) {
        Set<Role> roleList = new HashSet<>();
        Role foundedRole;

        userSec.setPassword(userService.encriptPassword(userSec.getPassword()));

        for (Role role : userSec.getRolesList()) {
            foundedRole = roleService.findById(role.getId()).orElse(null);
            if (foundedRole != null) roleList.add(foundedRole);
        }

        userSec.setRolesList(roleList);
        Optional<UserSec> newUser = userService.save(userSec);
        return newUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
