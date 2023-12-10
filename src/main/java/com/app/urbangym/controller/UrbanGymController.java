package com.app.urbangym.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.urbangym.entitys.Administrator;
import com.app.urbangym.entitys.Coach;
import com.app.urbangym.entitys.Plan;
import com.app.urbangym.entitys.User;
import com.app.urbangym.repository.AdministratorRepository;
import com.app.urbangym.repository.CoachRepository;
import com.app.urbangym.repository.PlanRepository;
import com.app.urbangym.repository.UserRepository;

// Esto lo deben ver todos, aun sin iniciar sesión
// Muestra la pag. de inicio de urbangym, muestra la lista de los planes que tiene urbangym
@Controller
@RequestMapping("/urbangym")
public class UrbanGymController {

    @Autowired
    @Qualifier("planRepository")
    private PlanRepository planRepository;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("administratorRepository")
    private AdministratorRepository administratorRepository;

    @Autowired
    @Qualifier("coachRepository")
    private CoachRepository coachRepository;

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

    @GetMapping
    public String verInicioUrbanGym(Model model, HttpSession session) {
        model.addAttribute("plans", planRepository.findAll());
        model.addAttribute("sesion_user", session.getAttribute("id_user"));
        model.addAttribute("rol_session", session.getAttribute("rol_session"));
        if (session.getAttribute("id_user") == null) {
            System.out.println("Cargamos el index y el id es: null");
        }

        System.out.println(session.getAttribute("id_user"));
        return "index";
    }

    @GetMapping("/foto/{id}")
    public ResponseEntity<byte[]> obtenerFotoPorId(@PathVariable Long id) {
        Optional<Plan> planOptional = planRepository.findById(id);
        if (planOptional.isPresent()) {
            Plan plan = planOptional.get();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(plan.getPhoto());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistrarNuevoUsuario(Model model) {
        model.addAttribute("titulo", "Registrar nuevo usuario");
        model.addAttribute("user", new User());
        return "formulario_nuevo_usuario";
    }

    @PostMapping("/register/guardar")
    public String guardarNuevoUsuario(String repassword, @Valid User user, BindingResult result, Model model,
            @RequestParam(name = "file", required = false) MultipartFile file) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registrar nuevo usuario");
            return "formulario_nuevo_usuario";
        }

        if (!repassword.equals(user.getPassword())) {
            model.addAttribute("aviso", "Las contraseñas deben coincidir");
            return "formulario_nuevo_usuario";
        }

        List<User> listaUsuarios = userRepository.findAll();

        for(User u : listaUsuarios) {
            if (u.getDni().toString().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_usuario";
            } else if (u.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_usuario";
            }
        }

        List<Administrator> listaAdministradores = administratorRepository.findAll();

        for (Administrator admin : listaAdministradores) {
            if (admin.getDni().toString().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_usuario";
            } else if (admin.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_usuario";
            }
        }

        List<Coach> listaCoachs = coachRepository.findAll();

        for (Coach coach : listaCoachs) {
            if (coach.getDni().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_usuario";
            } else if (coach.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_usuario";
            }
        }

        if (!file.isEmpty()) {
            try {
                user.setPhoto(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                File defaultImage = new File("src/main/resources/static/images/photo_no_image.png");
                byte[] defaultImageBytes = Files.readAllBytes(defaultImage.toPath());
                user.setPhoto(defaultImageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (user.getCoach() == null) {
            user.setCoach(null);
        }

        if (user.getMemberships() == null) {
            user.setMemberships(null);
        }

        user.setPassword(passEncode.encode(user.getPassword()));
        userRepository.save(user);
        // try {
            
        // } catch (DataIntegrityViolationException e) {
        //     if (e != null) {
        //         String errorMessage = e.getMessage();
        //         if (errorMessage != null) {
        //             if (errorMessage.contains("dni")) {
        //                 model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
        //             } else if (errorMessage.contains("email")) {
        //                 model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
        //             }
        //         }
        //     }
        //     return "formulario_nuevo_usuario";
        // }

        model.addAttribute("titulo", "registro exitoso");
        return "redirect:/urbangym/register?register_success";
    }

    // Es solo para crear a los administradores
    @GetMapping("/registrar_admin")
    public String verFormRegistrarAdministradores(Model model) {
        model.addAttribute("admin", new Administrator());
        return "formulario_nuevo_administrador";
    }

    @PostMapping("/registrar_admin/guardar")
    public String registrarNuevoAdministrador(@Valid @ModelAttribute("admin") Administrator admin,BindingResult result, String repassword,
            Model model) {

        if (result.hasErrors()) {
            return "formulario_nuevo_administrador";
        }

        if (!repassword.equals(admin.getPassword())) {
            model.addAttribute("aviso", "Las contraseñas deben coincidir");
            return "formulario_nuevo_administrador";
        }

        List<User> listaUsuarios = userRepository.findAll();

        for (User user : listaUsuarios) {
            if (user.getDni().equals(admin.getDni().toString())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_administrador";
            } else if (admin.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_administrador";
            }
        }

        List<Coach> listaCoachs = coachRepository.findAll();

        for (Coach coach : listaCoachs) {
            if (coach.getDni().equals(admin.getDni().toString())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_administrador";
            } else if (coach.getEmail().equals(admin.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "formulario_nuevo_administrador";
            }
        }

        admin.setPassword(passEncode.encode(admin.getPassword()));
        administratorRepository.save(admin);
        return "redirect:/urbangym/registrar_admin?exito";

    }

}
