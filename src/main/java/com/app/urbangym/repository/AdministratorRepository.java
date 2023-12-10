package com.app.urbangym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.urbangym.entitys.Administrator;

@Repository("administratorRepository")
public interface AdministratorRepository extends JpaRepository<Administrator, Long>{
    public Optional<Administrator> findByEmail(String email);
}
