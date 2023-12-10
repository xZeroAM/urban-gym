package com.app.urbangym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import com.app.urbangym.entitys.Exercise;

@Controller("exerciseRepository")
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    
}
