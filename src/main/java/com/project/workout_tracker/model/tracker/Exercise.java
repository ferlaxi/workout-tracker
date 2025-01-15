package com.project.workout_tracker.model.tracker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUser;
    private String name;
    private String description;
    private String category;
    private Integer repetition;
    private Integer series;
    private Double weight;

    @ManyToMany(mappedBy = "exerciseList")
    @JsonIgnore
    private Set<Plan> planList = new HashSet<>();

    public Exercise() {
    }

    public Exercise(Long id, Long idUser, String name, String description, String category, Integer repetition, Integer series, Double weight, Set<Plan> planList) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.description = description;
        this.category = category;
        this.repetition = repetition;
        this.series = series;
        this.weight = weight;
        this.planList = planList;
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

    public Integer getRepetition() {
        return repetition;
    }

    public Integer getSeries() {
        return series;
    }

    public Double getWeight() {
        return weight;
    }

    public Set<Plan> getPlanList() {
        return planList;
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

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setPlanList(Set<Plan> planList) {
        this.planList = planList;
    }
}
