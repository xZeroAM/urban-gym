package com.app.urbangym.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.urbangym.entitys.Administrator;
import com.app.urbangym.entitys.Coach;

import org.springframework.security.core.userdetails.User;

//busca usuario y lo pasa a otra parte de seguridad
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private UserService userService;

    // Para la parte de encriptar y desencriptar la clave
    // Cuando nos registremos la clave se encripta y cuando lo nesecita la
    // desencripta o algo asi xd
    // private BCryptPasswordEncoder bCrypt;

    @Autowired
    HttpSession session;

    // Busca al usuario por su username (en este caso email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<Administrator> optionalAdmin = adminService.findByEmail(username);
        if (optionalAdmin.isPresent()) {
            session.setAttribute("id_user", optionalAdmin.get().getDni());
            Administrator administrator = optionalAdmin.get();
            return User.builder().username(administrator.getName()).password(administrator.getPassword())
                    .roles("ADMIN").build();
        }

        Optional<Coach> optionalCoach = coachService.findByEmail(username);
        if (optionalCoach.isPresent()) {
            session.setAttribute("id_user", optionalCoach.get().getId_coach());
            Coach coach = optionalCoach.get();
            return User.builder().username(coach.getName()).password(coach.getPassword())
                    .roles("COACH").build();
        }

        Optional<com.app.urbangym.entitys.User> optionalUser = userService.findByEmail(username);
        if (optionalUser.isPresent()) {
            session.setAttribute("id_user", optionalUser.get().getId_user());
            com.app.urbangym.entitys.User usuario = optionalUser.get();
            return User.builder().username(usuario.getName()).password(usuario.getPassword())
                    .roles("USER").build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado");

    }

}
