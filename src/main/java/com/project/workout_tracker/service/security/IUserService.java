package com.project.workout_tracker.service.security;

import com.project.workout_tracker.model.security.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<UserSec> findAll();
    public Optional<UserSec> findById (Long id);
    public Optional<UserSec> save (UserSec userSec);
    public void deleteById (Long id);
    public Optional<UserSec> update (Long id, UserSec userSec);
    public String encriptPassword (String password);
}
