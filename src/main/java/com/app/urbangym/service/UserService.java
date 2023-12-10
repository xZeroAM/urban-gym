package com.app.urbangym.service;

import java.util.List;
import java.util.Optional;

import com.app.urbangym.entitys.User;

public interface UserService {
    Optional<User> findByEmail(String email);

    public List<User> listAll(String keyWord);
}
