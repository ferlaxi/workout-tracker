package com.project.workout_tracker.model.tracker;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUser;
    private String name;
    private String description;
    private String category;

    @Temporal(TemporalType.DATE)
    private LocalDate initDate;

    @Temporal(TemporalType.TIME)
    private LocalTime initTime;
    private String comentary;
    private String state;

    @ManyToMany
    @JoinTable(
            name = "plan_ejercicios",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "ejercicio_id")
    )
    private Set<Exercise> exerciseList = new HashSet<>();

    public Plan() {
    }

    public Plan(Long id, Long idUser, String name, String description, String category, LocalDate initDate, LocalTime initTime, String comentary, String state, Set<Exercise> exerciseList) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.description = description;
        this.category = category;
        this.initDate = initDate;
        this.initTime = initTime;
        this.comentary = comentary;
        this.state = state;
        this.exerciseList = exerciseList;
    }

    public Long getId() {
        return id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public LocalTime getInitTime() {
        return initTime;
    }

    public String getComentary() {
        return comentary;
    }

    public String getState() {
        return state;
    }

    public Set<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public void setInitTime(LocalTime initTime) {
        this.initTime = initTime;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setExerciseList(Set<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
}
