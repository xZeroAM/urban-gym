package com.app.urbangym.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.urbangym.entitys.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{

    public Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.name LIKE %?1% OR u.lastName LIKE %?1% OR u.dni LIKE %?1% OR u.cellPhone LIKE %?1% OR u.email LIKE %?1%")
    public List<User> findAllByKeyword(String keyword);

}
