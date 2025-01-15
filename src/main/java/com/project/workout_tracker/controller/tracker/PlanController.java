package com.project.workout_tracker.controller.tracker;

import com.project.workout_tracker.model.security.UserSec;
import com.project.workout_tracker.model.tracker.Exercise;
import com.project.workout_tracker.model.tracker.Plan;
import com.project.workout_tracker.repository.security.IUserRepository;
import com.project.workout_tracker.service.tracker.IExerciseService;
import com.project.workout_tracker.service.tracker.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/training")
@PreAuthorize("isAuthenticated()")
public class PlanController {

    @Autowired
    private IPlanService planService;

    @Autowired
    private IExerciseService exerciseService;

    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity<Plan> createPlan (@RequestBody Plan plan) {
        Set<Exercise> exerciseList = new HashSet<>();
        Exercise exerciseFounded;
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));
        plan.setIdUser(userSec.getId());

        for (Exercise exercise : plan.getExerciseList()) {
            exerciseFounded = exerciseService.findById(exercise.getId()).orElse(null);
            if (exerciseFounded != null) {
                exerciseList.add(exerciseFounded);
            }
        }

        plan.setExerciseList(exerciseList);
        plan.setIdUser((userSec.getId()));
        Optional<Plan> newPlan = planService.save(plan);
        return newPlan.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlans () {
        return new ResponseEntity<>(planService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById (@PathVariable Long id) {
        Optional<Plan> plan = planService.findById(id);
        return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Plan> updatePlan (@PathVariable Long id,
                                            @RequestBody Plan editedPlan) {
        Optional<Plan> plan = planService.update(id, editedPlan);
        return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlan (@PathVariable Long id) {
        planService.delete(id);
        return new ResponseEntity<>("successfully removed", HttpStatus.NO_CONTENT);
    }
}
