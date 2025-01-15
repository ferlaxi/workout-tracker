package com.project.workout_tracker.controller.tracker;

import com.project.workout_tracker.model.security.UserSec;
import com.project.workout_tracker.model.tracker.Exercise;
import com.project.workout_tracker.repository.security.IUserRepository;
import com.project.workout_tracker.service.tracker.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercise")
@PreAuthorize("isAuthenticated()")
public class ExerciseController {

    @Autowired
    private IExerciseService exerciseService;

   @Autowired
   private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity<Exercise> createExercise (@RequestBody Exercise exercise) {
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));
        exercise.setIdUser(userSec.getId());
        Optional<Exercise> newExercise = exerciseService.save(exercise);
        return newExercise.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() ->  ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises () {
        return new ResponseEntity<>(exerciseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> findExerciseById (@PathVariable Long id) {
        Optional<Exercise> exercise = exerciseService.findById(id);
        return exercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Exercise> updateExercise (@PathVariable Long id,
                                          @RequestBody Exercise editedExercise) {
        Optional<Exercise> updatedExercise = exerciseService.update(id, editedExercise);
        return updatedExercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExercise (@PathVariable Long id) {
        exerciseService.delete(id);
        return new ResponseEntity<>("successfully removed", HttpStatus.NO_CONTENT);
    }
 }
