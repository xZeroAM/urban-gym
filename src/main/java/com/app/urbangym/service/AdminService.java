package com.app.urbangym.service;

import java.util.Optional;

import com.app.urbangym.entitys.Administrator;

public interface AdminService {
    Optional<Administrator> findByEmail(String email);
}
