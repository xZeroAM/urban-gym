package com.app.urbangym.entitys;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "DAY")
public class Day {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_DAY")
    private long id_day;

    @Column(name = "DAY_NAME")
    private String day_name;

    @ManyToMany(mappedBy = "days")
    private List<Routine> routines;

    public long getId_day() {
        return this.id_day;
    }

    public void setId_day(long id_day) {
        this.id_day = id_day;
    }

    public String getDay_name() {
        return this.day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public List<Routine> getRoutines() {
        return this.routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }

}
