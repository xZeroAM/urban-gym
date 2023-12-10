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
import org.springframework.data.repository.query.Param;
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
import com.app.urbangym.entitys.User;
import com.app.urbangym.repository.AdministratorRepository;
import com.app.urbangym.repository.CoachRepository;
import com.app.urbangym.repository.UserRepository;
import com.app.urbangym.service.CoachServiceImpl;

// El administrador debe ver esta parte
// Muestra formulario de coach, lo guarda, tiene el metodo para mostrar las imagenes del coach y muestra una tabla listando todos los coachs
@Controller
@RequestMapping("/urbangym/admin/coach")
public class AdminCoachController {

    @Autowired
    @Qualifier("coachRepository")
    private CoachRepository coachRepository;

    @Autowired
    private CoachServiceImpl coachServiceImpl;

    @Autowired
    @Qualifier("administratorRepository")
    private AdministratorRepository administratorRepository;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

    // Muestra el form del coach
    @GetMapping("/form")
    public String verFormularioCoach(Model model, HttpSession session) {
        Administrator a = administratorRepository.findById((Long) session.getAttribute("id_user")).get();
        Coach c = new Coach();
        c.setAdmin(a);
        System.out.println(c.getAdmin().getDni());
        model.addAttribute("titulo", "Añadir nuevo coach");
        model.addAttribute("coach", c);

        return "form_coach";
    }

    // Guarda al coach
    @PostMapping("/form/guardar")
    public String guardarCoach(String repassword, @ModelAttribute("coach") @Valid Coach coach, BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile file, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Añadir nuevo coach");
            return "form_coach";
        }

        if (!repassword.equals(coach.getPassword())) {
            model.addAttribute("titulo", "Añadir nuevo coach");
            model.addAttribute("aviso", "Las contraseñas deben coincidir");
            return "form_coach";
        }

        List<User> listaUsuarios = userRepository.findAll();

        for(User u : listaUsuarios) {
            if (u.getDni().toString().equals(coach.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_coach";
            } else if (u.getEmail().equals(coach.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_coach";
            }
        }

        List<Administrator> listaAdministradores = administratorRepository.findAll();

        for (Administrator admin : listaAdministradores) {
            if (admin.getDni().toString().equals(coach.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_coach";
            } else if (admin.getEmail().equals(coach.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_coach";
            }
        }

        List<Coach> listaCoachs = coachRepository.findAll();

        for (Coach c : listaCoachs) {
            if (c.getDni().equals(coach.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_coach";
            } else if (c.getEmail().equals(coach.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_coach";
            }
        }

        Optional<Coach> coachOptional = coachRepository.findById(coach.getId_coach());

        if (coachOptional.isPresent()) {

            Coach coachExistente = coachOptional.get();

            if (!file.isEmpty()) {
                try {
                    coach.setPhoto(file.getBytes());
                } catch (Exception e) {

                }
            } else {
                coach.setPhoto(coachExistente.getPhoto());
            }

        } else {
            if (!file.isEmpty()) {
                try {
                    coach.setPhoto(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    File defaultImage = new File("src/main/resources/static/images/photo_no_image.png");
                    byte[] defaultImageBytes = Files.readAllBytes(defaultImage.toPath());
                    coach.setPhoto(defaultImageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Administrator admin = coach.getAdmin();
        // List<Coach> listaCoachDelAdmin = admin.getCoachs();
        // listaCoachDelAdmin.add(coach);
        // admin.setCoachs(listaCoachDelAdmin);
        model.addAttribute("titulo", "Registro de nuevo coach exitoso");
        coach.setPassword(passEncode.encode(coach.getPassword()));
        coachRepository.save(coach);
        return "redirect:/urbangym/admin/coach/form?save_success";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCoach(@PathVariable(name = "id") Long id,Model model) {
        model.addAttribute("coach", coachRepository.findById(id));
        return "form_coach_edit";
    }

    @PostMapping("/editar/guardar")
    public String editarCoach(@RequestParam String dni, @RequestParam String name, @RequestParam String lastName,
            @RequestParam String email, @Valid Coach coach, BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile file, Model model) {

        if(result.hasErrors()) {
            return "form_coach_edit";
        }

        List<User> listaUsuarios = userRepository.findAll();

        for(User u : listaUsuarios) {
            if (u.getDni().toString().equals(coach.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_coach_edit";
            } else if (u.getEmail().equals(coach.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_coach_edit";
            }
        }

        List<Administrator> listaAdministradores = administratorRepository.findAll();

        for (Administrator admin : listaAdministradores) {
            if (admin.getDni().toString().equals(coach.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_coach_edit";
            } else if (admin.getEmail().equals(coach.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_coach_edit";
            }
        }

        List<Coach> listaCoachs = coachRepository.findAll();
        listaCoachs.remove(coachRepository.findById(coach.getId_coach()).get());

        for (Coach c : listaCoachs) {
            if (c.getDni().equals(coach.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_coach_edit";
            } else if (c.getEmail().equals(coach.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_coach_edit";
            }
        }

        Optional<Coach> optionalCoach = coachRepository.findById(coach.getId_coach());
        Coach coachEdit = coach;
        coachEdit.setDni(dni);
        coachEdit.setName(name);
        coachEdit.setLastName(lastName);
        coachEdit.setEmail(email);

        if (optionalCoach.isPresent()) {
            Coach coachExistente = optionalCoach.get();

            if (!file.isEmpty()) {
                try {
                    coachEdit.setPhoto(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                coachEdit.setPhoto(coachExistente.getPhoto());
            }
            coachEdit.setUsers(coachExistente.getUsers());
            coachEdit.setPassword(coachExistente.getPassword());
        }

        coachRepository.save(coachEdit);

        return "redirect:/urbangym/admin/coach/editar/" + coach.getId_coach() + "?edit_success";
    }

    // Obtengo la foto del coach por medio de su Id
    @GetMapping("/foto/{id}")
    public ResponseEntity<byte[]> obtenerFotoPorId(@PathVariable Long id) {
        Optional<Coach> userOptional = coachRepository.findById(id);
        if (userOptional.isPresent()) {
            Coach coach = userOptional.get();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(coach.getPhoto());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Muestra una tabla con la lista de coachs
    // muestra html lista_coachs y envia una lista
    @GetMapping("/lista_coachs")
    public String verListaDeCoachs(Model model, @Param("keyWord") String keyWord) {
        model.addAttribute("coachs", coachServiceImpl.listAll(keyWord));
        model.addAttribute("keyWord", keyWord);
        return "lista_coachs";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCoach(@PathVariable(name = "id") Long id) {
        Coach coach = coachRepository.findById(id).orElse(null);
        if (coach != null) {
            List<User> users = coach.getUsers();
            for (User user : users) {
                user.setCoach(null);
                userRepository.save(user);
            }
            coachRepository.delete(coach);
        }
        return "redirect:/urbangym/admin/coach/lista_coachs?delete_success";
    }

}
