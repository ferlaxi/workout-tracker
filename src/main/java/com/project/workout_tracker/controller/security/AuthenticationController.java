package com.project.workout_tracker.controller.security;

import com.project.workout_tracker.dto.AuthLoginRequestDTO;
import com.project.workout_tracker.dto.AuthResponseDTO;
import com.project.workout_tracker.model.security.Role;
import com.project.workout_tracker.model.security.UserSec;
import com.project.workout_tracker.service.security.IRoleService;
import com.project.workout_tracker.service.security.IUserService;
import com.project.workout_tracker.service.security.UserDetailsServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login (@RequestBody @Valid AuthLoginRequestDTO userRequest) {
        return new ResponseEntity<>(userDetailsServiceImp.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserSec> createUser (@RequestBody UserSec userSec) {
        Set<Role> roleList = new HashSet<>();
        Role foundedRole;

        foundedRole = roleService.findById(1L).orElse(null);
        if (foundedRole != null) roleList.add(foundedRole);

        userSec.setPassword(userService.encriptPassword(userSec.getPassword()));
        userSec.setEnabled(true);
        userSec.setAccountNotExpired(true);
        userSec.setAccountNotLocked(true);
        userSec.setCredentialNotExpired(true);
        userSec.setRolesList(roleList);

        Optional<UserSec> newUser = userService.save(userSec);
        return newUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
