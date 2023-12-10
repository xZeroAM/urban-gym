package com.app.urbangym.service;

import java.util.List;
import java.util.Optional;

import com.app.urbangym.entitys.Coach;

public interface CoachService {

    Optional<Coach> findByEmail(String email);

    public List<Coach> listAll(String keyWord);
    
}
