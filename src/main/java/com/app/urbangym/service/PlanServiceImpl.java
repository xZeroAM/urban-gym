package com.app.urbangym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.app.urbangym.entitys.Plan;
import com.app.urbangym.repository.PlanRepository;

@Service
public class PlanServiceImpl implements PlanService {
    
    @Autowired
    @Qualifier("planRepository")
    private PlanRepository planRepository;

    @Override
    public List<Plan> listAll(String keyWord) {
        if(keyWord != null) {
            return planRepository.findAllByKeyword(keyWord);
        } else {
            return planRepository.findAll();
        }
    }

}
