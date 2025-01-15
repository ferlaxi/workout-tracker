package com.project.workout_tracker.service.tracker;

import com.project.workout_tracker.model.tracker.Plan;

import java.util.List;
import java.util.Optional;

public interface IPlanService {
    public Optional<Plan> save (Plan plan);
    public List<Plan> findAll ();
    public Optional<Plan> findById (Long id);
    public Optional<Plan> update (Long id, Plan editedPlan);
    public void delete (Long id);
}
