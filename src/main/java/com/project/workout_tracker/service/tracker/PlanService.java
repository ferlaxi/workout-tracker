package com.project.workout_tracker.service.tracker;

import com.project.workout_tracker.model.security.UserSec;
import com.project.workout_tracker.model.tracker.Plan;
import com.project.workout_tracker.repository.security.IUserRepository;
import com.project.workout_tracker.repository.tracker.IPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService implements IPlanService{

    @Autowired
    private IPlanRepository planRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Optional<Plan> save(Plan plan) {
        return Optional.of(planRepository.save(plan));
    }

    @Override
    public List<Plan> findAll() {
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));
        return planRepository.findAllById(userSec.getId());
    }

    @Override
    public Optional<Plan> findById(Long id) {
        Optional<Plan> planFinded = planRepository.findById(id);
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));

        if (planFinded.isPresent() && planFinded.get().getId() == userSec.getId()) {
            return planFinded;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Plan> update(Long id, Plan editedPlan) {
        Optional<Plan> plan = this.findById(id);
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));

        if (plan.isPresent() && plan.get().getId() == userSec.getId()) {
            plan.get().setName(editedPlan.getName());
            plan.get().setDescription(editedPlan.getDescription());
            plan.get().setCategory(editedPlan.getCategory());
            plan.get().setInitDate(editedPlan.getInitDate());
            plan.get().setInitTime(editedPlan.getInitTime());
            plan.get().setComentary(editedPlan.getComentary());
            plan.get().setState(editedPlan.getState());
            return this.save(plan.get());
        }

        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        Optional<Plan> planFinded = planRepository.findById(id);
        UserSec userSec = userRepository.findUserEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("Bad Request"));

        if (planFinded.isPresent() && planFinded.get().getId() == userSec.getId()) {
            planRepository.deleteById(id);
        }
    }
}
