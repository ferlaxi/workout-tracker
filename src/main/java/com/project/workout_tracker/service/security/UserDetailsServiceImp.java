package com.project.workout_tracker.service.security;

import com.project.workout_tracker.dto.AuthLoginRequestDTO;
import com.project.workout_tracker.dto.AuthResponseDTO;
import com.project.workout_tracker.model.security.UserSec;
import com.project.workout_tracker.repository.security.IUserRepository;
import com.project.workout_tracker.security.config.SecurityConfig;
import com.project.workout_tracker.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserSec userSec = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));

        List<GrantedAuthority> authorityList = new ArrayList<>();

        userSec.getRolesList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        userSec.getRolesList().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add( new SimpleGrantedAuthority(permission.getPermission())));

        return new User(userSec.getUsername(),
                userSec.getPassword(),
                userSec.getEnabled(),
                userSec.getAccountNotExpired(),
                userSec.getCredentialNotExpired(),
                userSec.getAccountNotLocked(),
                authorityList);
    }

    public AuthResponseDTO loginUser (AuthLoginRequestDTO authLoginRequestDTO) {

        String username = authLoginRequestDTO.username();
        String password = authLoginRequestDTO.password();

        Authentication authentication = this.authenticate (username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponseDTO(username, "login ok", accessToken, true);
    }

    public Authentication authenticate (String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!securityConfig.passwordEncoder().matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public UserSec register (AuthLoginRequestDTO authLoginRequestDTO) {
        UserSec newUser = new UserSec();
        newUser.setUsername(authLoginRequestDTO.username());
        newUser.setPassword(authLoginRequestDTO.password());
        newUser.setEnabled(true);
        userRepository.save(newUser);
        return newUser;
    }
}
