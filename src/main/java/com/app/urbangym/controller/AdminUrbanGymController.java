package com.app.urbangym.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.urbangym.entitys.Membership;
import com.app.urbangym.entitys.User;
import com.app.urbangym.repository.CoachRepository;
import com.app.urbangym.repository.PlanRepository;
import com.app.urbangym.repository.UserRepository;

// Solo lo debe ver el admin
// Muestra la pag. del admin donde vera una nav donde estan los links para agregar: usuarios, plan, coachs y listarlos y hacerles sus cambios

@Controller
@RequestMapping("/urbangym/admin")
public class AdminUrbanGymController {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("coachRepository")
    private CoachRepository coachRepository;

    @Autowired
    @Qualifier("planRepository")
    private PlanRepository planRepository;

    @GetMapping("")
    public String verIndexDeAdmin(Model model) {
        List<User> lista_users = userRepository.findAll();
        List<User> lista_users_activos = userRepository.findAll();
        for(User u : lista_users) {
            for(Membership m : u.getMemberships()) {
                if(m.getMembershipStatus().getStatus() == true) {
                    lista_users_activos.add(u);
                    break;
                }
            }
        }

        model.addAttribute("cantidad_usuarios", userRepository.findAll().size());
        model.addAttribute("cantidad_coachs", coachRepository.findAll().size());
        model.addAttribute("cantidad_planes", planRepository.findAll().size());
        model.addAttribute("cantidad_usuarios_activos", lista_users_activos.size());
        return "index_admin";

    }

}
