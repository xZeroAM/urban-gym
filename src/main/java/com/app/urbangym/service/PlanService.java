package com.app.urbangym.service;

import java.util.List;

import com.app.urbangym.entitys.Plan;

public interface PlanService {

    public List<Plan> listAll(String keyWord);

}
