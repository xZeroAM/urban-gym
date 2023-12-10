package com.app.urbangym.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.app.urbangym.entitys.Day;
import com.app.urbangym.entitys.Routine;
import com.app.urbangym.entitys.RoutineDay;
import com.app.urbangym.entitys.RoutineDayID;
import com.app.urbangym.repository.RoutineDayRepository;
import com.app.urbangym.repository.RoutineRepository;

@Service
@Qualifier("routineDayServiceImpl")
public class routineDayServiceImpl implements routineDayService{

    @Autowired
    @Qualifier("routineDayRepository")
    private RoutineDayRepository routineDayRepository;

    @Autowired
    @Qualifier("routineRepository")
    private RoutineRepository routineRepository;

    @Override
    public List<RoutineDay> findRoutineDayByRoutineIdAndDayList(Long routine_id, List<Day> days) {
        
        List<RoutineDayID> rdList = new ArrayList<>();
        List<RoutineDay> rd = new ArrayList<>();

        for(Day day : days){
            RoutineDayID rdId = new RoutineDayID();
            rdId.setId_routine(routineRepository.findById(routine_id).get());
            rdId.setId_day(day);
            rdList.add(rdId);
        }

        rd = routineDayRepository.findAllById(rdList);

        return rd;
    }

    @Override
    public RoutineDay findRoutineDayByRoutineAndDay(Long routine_id, Day day) {
        RoutineDayID rdId = new RoutineDayID();
        Routine routine = routineRepository.findById(routine_id).orElse(null);
        rdId.setId_routine(routine);
        rdId.setId_day(day);
        RoutineDay rd = routineDayRepository.findById(rdId).get();
        return rd;
    }
    
}
