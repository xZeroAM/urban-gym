package com.app.urbangym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.urbangym.entitys.RoutineDay;
import com.app.urbangym.entitys.RoutineDayID;

@Repository("routineDayRepository")
public interface RoutineDayRepository extends JpaRepository<RoutineDay, RoutineDayID>{
    
}