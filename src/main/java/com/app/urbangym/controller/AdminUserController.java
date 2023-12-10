package com.app.urbangym.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.urbangym.entitys.Administrator;
import com.app.urbangym.entitys.Coach;
import com.app.urbangym.entitys.Membership;
import com.app.urbangym.entitys.MembershipStatus;
import com.app.urbangym.entitys.User;
import com.app.urbangym.repository.AdministratorRepository;
import com.app.urbangym.repository.CoachRepository;
import com.app.urbangym.repository.MembershipRepository;
import com.app.urbangym.repository.MembershipStatusRepository;
import com.app.urbangym.repository.PlanRepository;
import com.app.urbangym.repository.UserRepository;
import com.app.urbangym.service.UserServiceImpl;

// Esto solo lo debe ver el administrador
// Muestra form del usuario, pero como vista para el admin, guardar, listar, agregar membresia, listar membresias de un usuario, agregar/cambiar coach
// y editar informacion del usuario
@Controller
@RequestMapping("/urbangym/admin/user")
public class AdminUserController {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("coachRepository")
    private CoachRepository coachRepository;

    @Autowired
    @Qualifier("planRepository")
    private PlanRepository planRepository;

    @Autowired
    @Qualifier("membershipRepository")
    private MembershipRepository membershipRepository;

    @Autowired
    @Qualifier("membershipStatusRepository")
    private MembershipStatusRepository membershipStatusRepository;

    @Autowired
    @Qualifier("administratorRepository")
    private AdministratorRepository administratorRepository;

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

    // Muestra el formulario del usuario
    @GetMapping("/form")
    public String verFormularioParaCrearNuevoUsuario(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("titulo", "Crear cuenta de nuevo usuario");
        return "form_user";
    }

    // Guarda el usuario
    // @PostMapping("/form/guardar")
    // public String guardarUsuario(String repassword, Membership membership,
    // @ModelAttribute("user") @Valid User user,
    // BindingResult result,
    // @RequestParam(name = "file", required = false) MultipartFile file, Model
    // model) {

    // if (result.hasErrors()) {
    // model.addAttribute("ls_coach", coachRepository.findAll());
    // model.addAttribute("titulo", "Crear cuenta de nuevo usuario");
    // return "form_user";
    // }

    // if (!repassword.equals(user.getPassword())) {
    // model.addAttribute("ls_coach", coachRepository.findAll());
    // model.addAttribute("aviso", "Las contraseñas deben coincidir");
    // return "form_user";
    // }

    // Optional<User> userOptional = userRepository.findById(user.getId_user());

    // if (userOptional.isPresent()) {
    // User userExistente = userOptional.get();

    // if (!file.isEmpty()) {
    // try {
    // user.setPhoto(file.getBytes());
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // } else {
    // user.setPhoto(userExistente.getPhoto());
    // }

    // if (user.getCoach() == null) {
    // user.setCoach(null);
    // }

    // if (user.getMemberships() == null) {
    // user.setMemberships(null);
    // }

    // } else {
    // if (!file.isEmpty()) {
    // try {
    // user.setPhoto(file.getBytes());
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // } else {
    // try {
    // File defaultImage = new
    // File("src/main/resources/static/images/photo_no_image.png");
    // byte[] defaultImageBytes = Files.readAllBytes(defaultImage.toPath());
    // user.setPhoto(defaultImageBytes);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // user.setPassword(passEncode.encode(user.getPassword()));
    // userRepository.save(user);

    // model.addAttribute("titulo", "Usuario guardado exitosamente");
    // model.addAttribute("ls_coach", coachRepository.findAll());
    // return "redirect:/urbangym/admin/user/form?save_success";
    // }

    @PostMapping("/form/guardar")
    public String guardarNuevoUsuario(String repassword, @Valid User user, BindingResult result, Model model,
            @RequestParam(name = "file", required = false) MultipartFile file) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registrar nuevo usuario");
            return "form_user";
        }

        if (!repassword.equals(user.getPassword())) {
            model.addAttribute("aviso", "Las contraseñas deben coincidir");
            return "form_user";
        }

        List<User> listaUsuarios = userRepository.findAll();

        for (User u : listaUsuarios) {
            if (u.getDni().toString().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_user";
            } else if (u.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_user";
            }
        }

        List<Administrator> listaAdministradores = administratorRepository.findAll();

        for (Administrator admin : listaAdministradores) {
            if (admin.getDni().toString().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_user";
            } else if (admin.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_user";
            }
        }

        List<Coach> listaCoachs = coachRepository.findAll();

        for (Coach coach : listaCoachs) {
            if (coach.getDni().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_user";
            } else if (coach.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_user";
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

        model.addAttribute("titulo", "registro exitoso");
        return "redirect:/urbangym/admin/user/form?save_success";
    }

    @Autowired
    private UserServiceImpl userServiceImpl;

    // Muestra una lista de los usuarios
    @GetMapping("/lista_usuarios")
    public String verListaDeUsuarios(Model model, @Param("keyWord") String keyWord) {
        // model.addAttribute("memberships", membershipRepository.findAll());
        model.addAttribute("users", userServiceImpl.listAll(keyWord));
        model.addAttribute("keyWord", keyWord);
        return "list_users";
    }

    // Carga la foto de los usuarios por su Id, sirve para listar los usuarios
    @GetMapping("/foto/{id}")
    public ResponseEntity<byte[]> obtenerFotoPorId(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(user.getPhoto());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Editar a los usuarios, esto solo edita el dni, nombre, apellidos, celular,
    // email y foto, cosas como el coach y rutinas se editan en otro lado
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        return "form_user_edit";
    }

    // Guarda el edit de los usuarios
    @PostMapping("/editar/guardar")
    public String editarUsuario(@RequestParam String dni, @RequestParam String name, @RequestParam String lastName,
            @RequestParam String cellPhone, @RequestParam String email, @Valid User user, BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile file, Model model) {

        if (result.hasErrors()) {
            return "form_user_edit";
        }

        List<User> listaUsuarios = userRepository.findAll();

        listaUsuarios.remove(userRepository.findById(user.getId_user()).get());
        for (User u : listaUsuarios) {
            if (u.getDni().toString().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_user_edit";
            } else if (u.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_user_edit";
            }
        }

        List<Administrator> listaAdministradores = administratorRepository.findAll();

        for (Administrator admin : listaAdministradores) {
            if (admin.getDni().toString().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_user_edit";
            } else if (admin.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_user_edit";
            }
        }

        List<Coach> listaCoachs = coachRepository.findAll();

        for (Coach coach : listaCoachs) {
            if (coach.getDni().equals(user.getDni())) {
                model.addAttribute("aviso_dni", "El DNI proporcionado ya está asociado a una cuenta existente");
                return "form_user_edit";
            } else if (coach.getEmail().equals(user.getEmail())) {
                model.addAttribute("aviso_email", "El Email proporcionado ya está asociado a una cuenta existente");
                return "form_user_edit";
            }
        }

        Optional<User> userOptional = userRepository.findById(user.getId_user());
        User userEdit = user;
        userEdit.setDni(dni);
        userEdit.setName(name);
        userEdit.setLastName(lastName);
        userEdit.setCellPhone(cellPhone);
        userEdit.setEmail(email);

        if (userOptional.isPresent()) {
            User userExistente = userOptional.get();

            if (!file.isEmpty()) {
                try {
                    userEdit.setPhoto(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                userEdit.setPhoto(userExistente.getPhoto());
            }
            userEdit.setCoach(userExistente.getCoach());
            userEdit.setPassword(userExistente.getPassword());
        }

        userRepository.save(userEdit);

        return "redirect:/urbangym/admin/user/editar/" + user.getId_user() + "?success";
    }

    // Agrega una membresia que sale de unir al usuario con un plan en un muchos a
    // muchos
    @GetMapping("/membresia/{id}")
    public String membresiaAgregarForm(@PathVariable(name = "id") Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        Membership membership = new Membership();
        membership.setUser(user);
        model.addAttribute("membership", membership);
        model.addAttribute("ls_plans", planRepository.findAll());
        return "membresia_user";
    }

    // Guarda una membresia a un usuario, se le agrega a la lista de membresias que
    // tiene
    @PostMapping("/membresia/guardar")
    public String agregarMembresia(@Valid Membership membership, BindingResult result, Model model) {

        if (result.hasErrors()) {

            model.addAttribute("ls_plans", planRepository.findAll());
            return "membresia_user";
        }

        User user = membership.getUser();
        MembershipStatus ms = new MembershipStatus();
        membership.setMembershipStatus(ms);
        ms.setMembership(membership);
        membership.updateStatus();

        membershipRepository.save(membership);

        return "redirect:/urbangym/admin/user/membresia/" + user.getId_user() + "?success";
    }

    @PostMapping("/membresia/editar")
    public String editarMembresia(@Valid Membership membership, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("ls_plans", planRepository.findAll());
            return "form_membresia_edit";
        }
        Membership membresiaAnterior = membershipRepository.findById(membership.getId()).get();

        membership.setMembershipStatus(membresiaAnterior.getMembershipStatus());
        membership.updateStatus();

        membershipRepository.save(membership);

        return "redirect:/urbangym/admin/user/lista/membresia/editar/" + membership.getId() + "?edit_success";
    }

    // Lista las membresias que tiene asignadas el usuario
    @GetMapping("/lista/membresia/{id}")
    public String verListaDeMembresiasDeUnUser(@PathVariable(name = "id") Long id, Model model) {

        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("title", "Lista de membresias de " + user.getName() + " " + user.getLastName());

        return "membresia_lista_user";
    }

    // Eliminar membresia de un usuario
    @GetMapping("/lista/membresia/eliminar/{id}")
    public String eliminarMembresiaDeUnUsuario(@PathVariable(name = "id") Long id, Model model) {
        Long idUser = membershipRepository.findById(id).get().getUser().getId_user();
        membershipRepository.delete(membershipRepository.findById(id).get());
        return "redirect:/urbangym/admin/user/lista/membresia/" + idUser + "?delete_success";
    }

    // Ver formulario para editar membresia de un usuario
    @GetMapping("/lista/membresia/editar/{id}")
    public String editarMembresiaDeUnUsuario(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("membership", membershipRepository.findById(id));
        model.addAttribute("ls_plans", planRepository.findAll());
        return "form_membresia_edit";
    }

    // Agrega un coach a un usuario
    @GetMapping("/coach/{id}")
    public String coachAgregarForm(@PathVariable(name = "id") Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("user_dni", id);
        model.addAttribute("lista_coachs", coachRepository.findAll());

        return "coach_user";
    }

    // Guarda el coach para el usuario
    @PostMapping("/coach/guardar")
    public String coachGuardar(@RequestParam(name = "id_user") Long id_user, Coach coach, Model model) {

        Optional<User> userOptional = userRepository.findById(id_user);
        User userSave = userOptional.orElse(null);
        Optional<Coach> coachOptional = Optional.empty();

        if (coach != null && coach.getId_coach() != 0) {
            coachOptional = coachRepository.findById(coach.getId_coach());
        }

        if (coachOptional.isPresent()) {
            userSave.setCoach(coachOptional.get());
            Coach c = coachOptional.get();
            c.getUsers().add(userSave);
            coachRepository.save(c);
        } else {
            userSave.setCoach(null);
        }

        userRepository.save(userSave);

        User user1 = userRepository.findById(id_user).orElse(null);
        model.addAttribute("user", user1);
        model.addAttribute("id_user", id_user);
        model.addAttribute("lista_coachs", coachRepository.findAll());

        return "redirect:/urbangym/admin/user/coach/" + id_user + "?success";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUser(@PathVariable(name = "id") Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            Coach coach = user.getCoach();
            if (coach != null) {
                coach.getUsers().remove(user);
                coachRepository.save(coach);
            }
        }

        userRepository.delete(user);

        return "redirect:/urbangym/admin/user/lista_usuarios?delete_success";
    }

}