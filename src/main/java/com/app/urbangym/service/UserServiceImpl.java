package com.app.urbangym.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.urbangym.entitys.User;
import com.app.urbangym.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> listAll(String keyWord) {
        if(keyWord != null) {
            return userRepository.findAllByKeyword(keyWord);
        }
        return userRepository.findAll();
    }
    
}
