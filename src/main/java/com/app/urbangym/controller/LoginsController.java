package com.app.urbangym.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.urbangym.repository.UserRepository;

// Estos son los logins, en teoria lo deberian ver todos 
@Controller
@RequestMapping("/urbangym/login")
public class LoginsController {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @GetMapping
    private String verLoginDeUsuario() {
        return "login_usuario";
    }

    @GetMapping("/acceder")
    public String loginUser(HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rolSession = "";
        if (auth != null && auth.getAuthorities() != null) {
            for (GrantedAuthority authority : auth.getAuthorities()) {
                rolSession = authority.getAuthority();
            }
        }

        session.setAttribute("rol_session", rolSession);
        System.out.println(session.getAttribute(rolSession));
        System.out.println(rolSession);

        return "redirect:/urbangym?login_success";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.removeAttribute("id_user");
        session.removeAttribute("rol_session");
        return "redirect:/urbangym";
    }

}
