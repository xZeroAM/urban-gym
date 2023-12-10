package com.app.urbangym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.urbangym.entitys.Routine;

@Repository("routineRepository")
public interface RoutineRepository extends JpaRepository<Routine, Long>{

}
