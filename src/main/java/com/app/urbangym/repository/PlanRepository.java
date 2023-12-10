package com.app.urbangym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.urbangym.entitys.Plan;

@Repository("planRepository")
public interface PlanRepository extends JpaRepository<Plan, Long>{
    
    @Query("SELECT p FROM Plan p WHERE p.title LIKE %?1% OR p.description LIKE %?1% OR p.duration LIKE %?1% OR p.price LIKE %?1%")
    public List<Plan> findAllByKeyword(String keyword);

}
