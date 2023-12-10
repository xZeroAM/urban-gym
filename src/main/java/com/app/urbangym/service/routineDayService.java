package com.app.urbangym.service;

import java.util.List;

import com.app.urbangym.entitys.Day;
import com.app.urbangym.entitys.RoutineDay;

public interface routineDayService {
    
    public List<RoutineDay> findRoutineDayByRoutineIdAndDayList(Long routine_id, List<Day> days);

    public RoutineDay findRoutineDayByRoutineAndDay(Long routine_id, Day day);

}
