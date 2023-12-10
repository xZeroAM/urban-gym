package com.app.urbangym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.urbangym.entitys.Day;

@Repository("dayRepository")
public interface DayRepository extends JpaRepository<Day, Long> {
    
}
