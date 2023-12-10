package com.app.urbangym.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EXERCISE")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_exercise;

    @Column(name = "NAME_EXERCISE")
    private String name_exercise;

    @Column(name = "SERIES_REPS")
    private String series_reps;

    @Column(name = "DESCRIPTION_EXERCISE")
    private String description_exercise;

    @Transient
    private String day;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ID_ROUTINE", referencedColumnName = "ID_ROUTINE"),
            @JoinColumn(name = "ID_DAY", referencedColumnName = "ID_DAY")
    })
    private RoutineDay routineDay;


    public Exercise() {
    }

    public long getId_exercise() {
        return this.id_exercise;
    }

    public void setId_exercise(long id_exercise) {
        this.id_exercise = id_exercise;
    }

    public String getName_exercise() {
        return this.name_exercise;
    }

    public void setName_exercise(String name_exercise) {
        this.name_exercise = name_exercise;
    }

    public String getSeries_reps() {
        return this.series_reps;
    }

    public void setSeries_reps(String series_reps) {
        this.series_reps = series_reps;
    }

    public String getDescription_exercise() {
        return this.description_exercise;
    }

    public void setDescription_exercise(String description_exercise) {
        this.description_exercise = description_exercise;
    }


    public RoutineDay getRoutineDay() {
        return this.routineDay;
    }

    public void setRoutineDay(RoutineDay routineDay) {
        this.routineDay = routineDay;
    }
    
    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
