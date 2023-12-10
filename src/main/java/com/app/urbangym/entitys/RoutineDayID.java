package com.app.urbangym.entitys;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Embeddable
public class RoutineDayID implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ROUTINE")
    private Routine id_routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DAY")
    private Day id_day;

    public Routine getId_routine() {
        return this.id_routine;
    }

    public void setId_routine(Routine id_routine) {
        this.id_routine = id_routine;
    }

    public Day getId_day() {
        return this.id_day;
    }

    public void setId_day(Day id_day) {
        this.id_day = id_day;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RoutineDayID)) {
            return false;
        }
        RoutineDayID routineDayID = (RoutineDayID) o;
        return Objects.equals(id_routine, routineDayID.id_routine) && Objects.equals(id_day, routineDayID.id_day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_routine, id_day);
    }

}
