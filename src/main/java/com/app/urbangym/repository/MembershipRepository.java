package com.app.urbangym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.urbangym.entitys.Membership;

@Repository("membershipRepository")
public interface MembershipRepository extends JpaRepository<Membership, Long>{
    
}
