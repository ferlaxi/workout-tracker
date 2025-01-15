package com.project.workout_tracker.repository.tracker;

import com.project.workout_tracker.model.tracker.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlanRepository extends JpaRepository<Plan, Long> {

    @Query("SELECT p FROM Plan p WHERE p.idUser = :idUser")
    public List<Plan> findAllById (Long IdUser);
}
