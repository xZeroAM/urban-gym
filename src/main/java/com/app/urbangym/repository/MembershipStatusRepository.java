package com.app.urbangym.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import com.app.urbangym.entitys.MembershipStatus;

@Controller
@Qualifier("membershipStatusRepository")
public interface MembershipStatusRepository extends JpaRepository<MembershipStatus, Long>{
    
}
