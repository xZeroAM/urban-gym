package com.app.urbangym.entitys;

import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROUTINE_DAY")
public class RoutineDay {

    @EmbeddedId
    private RoutineDayID id_routine_day;

    @OneToMany(mappedBy = "routineDay")
    private List<Exercise> exercises;

    public List<Exercise> getExercises() {
        return this.exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public RoutineDayID getId_routine_day() {
        return this.id_routine_day;
    }

    public void setId_routine_day(RoutineDayID id_routine_day) {
        this.id_routine_day = id_routine_day;
    }

}
