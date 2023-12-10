package com.app.urbangym.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.app.urbangym.entitys.Administrator;
import com.app.urbangym.repository.AdministratorRepository;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    @Qualifier("administratorRepository")
    private AdministratorRepository administratorRepository;

    @Override
    public Optional<Administrator> findByEmail(String email) {
        return administratorRepository.findByEmail(email);
    }

}
