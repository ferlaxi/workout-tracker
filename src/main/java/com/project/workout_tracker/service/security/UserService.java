package com.project.workout_tracker.service.security;

import com.project.workout_tracker.model.security.UserSec;
import com.project.workout_tracker.repository.security.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public List<UserSec> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return iUserRepository.findById(id);
    }

    @Override
    public Optional<UserSec> save(UserSec userSec) {
        UserSec newUser = iUserRepository.save(userSec);
        return Optional.of(newUser);
    }

    @Override
    public void deleteById(Long id) {
        iUserRepository.deleteById(id);
    }

    @Override
    public Optional<UserSec> update(Long id, UserSec userSec) {
        return Optional.empty();
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
