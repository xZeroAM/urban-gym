package com.app.urbangym.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.urbangym.entitys.Coach;

@Repository("coachRepository")
public interface CoachRepository extends JpaRepository<Coach, Long>{

    public Optional<Coach> findByEmail(String email);

    @Query("SELECT c FROM Coach c WHERE c.name LIKE %?1% OR c.lastName LIKE %?1% OR c.dni LIKE %?1% OR c.dni LIKE %?1% OR c.email LIKE %?1%")
    public List<Coach> findAllByKeyword(String keyword);
}
