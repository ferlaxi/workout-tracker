package com.project.workout_tracker.repository.tracker;

import com.project.workout_tracker.model.tracker.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("SELECT e FROM Exercise e WHERE e.idUser = :idUser")
    public List<Exercise> findAllById (Long idUSer);
}
