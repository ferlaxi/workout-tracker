package com.project.workout_tracker.repository.security;

import com.project.workout_tracker.model.security.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long> {

    public Optional<UserSec> findUserEntityByUsername (String username);
}
