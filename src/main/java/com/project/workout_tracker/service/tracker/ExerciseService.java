package com.project.workout_tracker.service.tracker;

import com.project.workout_tracker.model.security.UserSec;
import com.project.workout_tracker.model.tracker.Exercise;
import com.project.workout_tracker.repository.security.IUserRepository;
import com.project.workout_tracker.repository.tracker.IExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService implements IExerciseService{

    @Autowired
    private IExerciseRepository exerciseRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Optional<Exercise> save(Exercise exercise) {
        return Optional.of(exerciseRepository.save(exercise));
    }

    @Override
    public List<Exercise> findAll() {
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));
        return exerciseRepository.findAllById(userSec.getId());
    }

    @Override
    public Optional<Exercise> findById(Long id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));

        if (exercise.isPresent() && exercise.get().getId() == userSec.getId()) {
            return exerciseRepository.findById(id);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Exercise> update(Long id, Exercise editedExercise) {
        Optional<Exercise> exercise = this.findById(id);
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));

        if (exercise.isPresent() && exercise.get().getId() == userSec.getId()) {
            exercise.get().setName(editedExercise.getName());
            exercise.get().setDescription(editedExercise.getDescription());
            exercise.get().setCategory(editedExercise.getCategory());
            exercise.get().setRepetition(editedExercise.getRepetition());
            exercise.get().setSeries(editedExercise.getSeries());
            exercise.get().setWeight(editedExercise.getWeight());
            return this.save(exercise.get());
        }

        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        Optional<Exercise> exercise = this.findById(id);
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));

        if (exercise.isPresent() && exercise.get().getId() == userSec.getId()) {
            exerciseRepository.deleteById(id);
        }
    }
}
