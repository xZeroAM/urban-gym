package com.app.urbangym.entitys;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "ROUTINE")
public class Routine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ROUTINE")
    private long id_routine;

    @Column(name = "NAME_ROUTINE")
    private String name_routine;

    @Column(name = "DESCRIPTION_ROUTINE")
    private String description_routine;

    @Column(name = "DURATION")
    private String duration;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "ROUTINE_DAY",
        joinColumns = @JoinColumn(name = "ID_ROUTINE"),
        inverseJoinColumns = @JoinColumn(name = "ID_DAY")
    )
    private List<Day> days;


    public long getId_routine() {
        return this.id_routine;
    }

    public void setId_routine(long id_routine) {
        this.id_routine = id_routine;
    }

    public String getName_routine() {
        return this.name_routine;
    }

    public void setName_routine(String name_routine) {
        this.name_routine = name_routine;
    }

    public String getDescription_routine() {
        return this.description_routine;
    }

    public void setDescription_routine(String description_routine) {
        this.description_routine = description_routine;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Day> getDays() {
        return this.days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
    

}
