package com.app.urbangym.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.app.urbangym.entitys.Coach;
import com.app.urbangym.repository.CoachRepository;

@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    @Qualifier("coachRepository")
    private CoachRepository coachRepository;

    @Override
    public Optional<Coach> findByEmail(String email) {
        return coachRepository.findByEmail(email);
    }

    @Override
    public List<Coach> listAll(String keyWord) {
        if(keyWord != null) {
            return coachRepository.findAllByKeyword(keyWord);
        }
        return coachRepository.findAll();
    }
    
}
