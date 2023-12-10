package com.app.urbangym.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.urbangym.entitys.Day;
import com.app.urbangym.entitys.Routine;
import com.app.urbangym.entitys.RoutineDay;
import com.app.urbangym.entitys.User;
import com.app.urbangym.repository.RoutineRepository;
import com.app.urbangym.repository.UserRepository;
import com.app.urbangym.service.routineDayServiceImpl;

// Esto lo debe ver el usuario
// Muestra una lista de sus rutinas, se debe acceder a este link desde el nav principal
// Se puede ver las rutinas a detalle
@Controller
@RequestMapping("/urbangym/user/")
public class UserController {

    @Autowired
    @Qualifier("routineRepository")
    private RoutineRepository routineRepository;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("routineDayServiceImpl")
    private routineDayServiceImpl routineDayServiceImpl;

    // Muestra lista de rutinas del usuario
    @RequestMapping("/lista_rutinas")
    public String verListaDeRutinasParaUsuario(Model model, HttpSession session) {
        if (session.getAttribute("id_user") != null) {
            Optional<User> optionalUser = userRepository.findById( (Long) session.getAttribute("id_user"));
            if (optionalUser.isPresent()) {
                List<Routine> lista_rutinas = optionalUser.get().getRoutines();
                model.addAttribute("lista_rutinas", lista_rutinas);
                return "user_lista_routines";
            }
        }

        return "redirect:/urbangym/login";
    }

    // Muestra una rutina del usuario de forma detallada, mostrando los ejercicios y
    // los dias
    @RequestMapping("/lista_rutinas/{id_rutina}")
    public String verRutinaADetalle(@PathVariable(name = "id_rutina") Long id_rutina, Model model, HttpSession session) {

        Routine r = routineRepository.findById(id_rutina).get();
        List<Day> lista_Days = r.getDays();
        List<RoutineDay> listaRD = routineDayServiceImpl.findRoutineDayByRoutineIdAndDayList(id_rutina, lista_Days);

        model.addAttribute("rutina", r);
        model.addAttribute("lista_routine_day", listaRD);
        System.out.println(listaRD);
        return "user_lista_routines_completo";
    }

}
