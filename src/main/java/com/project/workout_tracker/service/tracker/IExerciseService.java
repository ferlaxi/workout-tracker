package com.project.workout_tracker.service.tracker;

import com.project.workout_tracker.model.tracker.Exercise;

import java.util.List;
import java.util.Optional;

public interface IExerciseService {
    public Optional<Exercise> save (Exercise exercise);
    public List<Exercise> findAll ();
    public Optional<Exercise> findById (Long id);
    public Optional<Exercise> update (Long id, Exercise editedExercise);
    public void delete (Long id);
}
