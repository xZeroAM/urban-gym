package com.app.urbangym.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.urbangym.entitys.Day;
import com.app.urbangym.entitys.Exercise;
import com.app.urbangym.entitys.Routine;
import com.app.urbangym.entitys.RoutineDay;
import com.app.urbangym.entitys.User;
import com.app.urbangym.repository.CoachRepository;
import com.app.urbangym.repository.DayRepository;
import com.app.urbangym.repository.ExerciseRepository;
import com.app.urbangym.repository.RoutineDayRepository;
import com.app.urbangym.repository.RoutineRepository;
import com.app.urbangym.repository.UserRepository;
import com.app.urbangym.service.routineDayServiceImpl;

// Esto solo lo pueden o deverian ver solo los coachs y talvez los admins
// Aqui al principio solo se muestra una lista de los usuarios que tiene asignado el coach
// y para cada usuario le permite agregarle una rutina, aunque aun no puede ver a detalle como quedo la rutina que le dio
// Estaria bien poner un boton para ver las rutinas del usuario, la logica para hacer esto esta en el userCountController
// pd: el userController no ya que este es solo para ver form y guardar el user.
@Controller
@RequestMapping("/urbangym/coach/")
public class CoachRoutineControlller {

    @Autowired
    @Qualifier("dayRepository")
    private DayRepository dayRepository;

    @Autowired
    @Qualifier("coachRepository")
    private CoachRepository coachRepository;

    @Autowired
    @Qualifier("routineRepository")
    private RoutineRepository routineRepository;

    @Autowired
    @Qualifier("routineDayRepository")
    private RoutineDayRepository routineDayRepository;

    @Autowired
    @Qualifier("routineDayServiceImpl")
    private routineDayServiceImpl routineDayServiceImpl;

    @Autowired
    @Qualifier("exerciseRepository")
    private ExerciseRepository exerciseRepository;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    // Muestra una lista de los usuarios que pertenecen a un coach, identificado por el {id}
    @RequestMapping("/list_users")
    public String mostrarListaUsuarioEntrenados(Model model, HttpSession session) {
        model.addAttribute("lista_usuarios_del_coach", coachRepository.findById( (Long) session.getAttribute("id_user")).get().getUsers());
        return "list_users_coach";
    }

    // Muestra el formulario de la rutina
    @RequestMapping("/routine/form/{id}")
    public String mostrarFormularioAgregarRutinaAUsuario(@PathVariable(name = "id") Long id_user, Model model) {
        Routine routine = new Routine();
        routine.setUser(userRepository.findById(id_user).get());
        model.addAttribute("objeto_routine", routine);
        model.addAttribute("lista_dias", dayRepository.findAll());
        model.addAttribute("objeto_ejercicio", new Exercise());
        return "form_routine";
    }

    // Guarda la rutina
    @PostMapping("/routine/guardar")
    public String guardarRutinaEstadoPruebas(@ModelAttribute(name = "objeto_routine") Routine objeto_routine,
    @ModelAttribute(name = "objeto_ejercicio") Exercise exercise, Model model, BindingResult result) {
        routineRepository.save(objeto_routine);

        List<Exercise> lista_ex = obtenerEjercicios(exercise);

        String[] elementos = exercise.getDay().split(",");
        List<Long> lista_diasLong = new ArrayList<>();

        for (String elemento : elementos) {
            Long dia = Long.parseLong(elemento);
            lista_diasLong.add(dia);
        }

        List<Day> lista_diasDay = new ArrayList<>();
        for(Long long_dia : lista_diasLong) {
            Day day = dayRepository.findById(long_dia).get();
            lista_diasDay.add(day);
        }

        for(int i = 0; i<lista_ex.size(); i++) {
            Exercise ex = lista_ex.get(i);
            Day day = lista_diasDay.get(i);
            RoutineDay routineDay = routineDayServiceImpl.findRoutineDayByRoutineAndDay(objeto_routine.getId_routine(), day);
            ex.setRoutineDay(routineDay);
            exerciseRepository.save(ex);
        }

        Routine routine = new Routine();
        routine.setUser(userRepository.findById(objeto_routine.getUser().getId_user()).get());
        model.addAttribute("objeto_routine", routine);
        model.addAttribute("lista_dias", dayRepository.findAll());
        model.addAttribute("objeto_ejercicio", new Exercise());

        return "form_routine";
    }

    // Como el @ModelAttribute(name = "objeto_ejercicio") Exercise exercise, me devuelve un string con los valores de todos los campos
    // como uno solo y separados por comas aqui lo que hago es obtener estos valores y asignarlos a objetos ejercicio que luego son guardados
    // en una lista que es retornada
    public List<Exercise> obtenerEjercicios(Exercise exercise) {

        String[] elementos1 = exercise.getName_exercise().split(",");
        String[] elementos2 = exercise.getSeries_reps().split(",");
        String[] elementos3 = exercise.getDescription_exercise().split(",");

        List<String> lista_nombre = new ArrayList<>();
        List<String> lista_series_reps = new ArrayList<>();
        List<String> lista_descripcion = new ArrayList<>();

        for (String elemento : elementos1) {
            lista_nombre.add(elemento);
        }

        for (String elemento : elementos2) {
            lista_series_reps.add(elemento);
        }

        for (String elemento : elementos3) {
            lista_descripcion.add(elemento);
        }

        List<Exercise> lista_ejercicios_sin_routineDay_asignado = new ArrayList<>();

        for (int i = 0; i < lista_nombre.size(); i++) {
            Exercise ex = new Exercise();
            ex.setName_exercise(lista_nombre.get(i));
            ex.setSeries_reps(lista_series_reps.get(i));
            ex.setDescription_exercise(lista_descripcion.get(i));
            lista_ejercicios_sin_routineDay_asignado.add(ex);
        }

        return lista_ejercicios_sin_routineDay_asignado;
    }

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

    @GetMapping("/routine/lista_rutinas/{id}")
    public String verListarRutinaDeUsuario(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("lista_rutinas", userRepository.findById(id).get().getRoutines());
        return "coach_user_lista_routines";
    }

    @GetMapping("/routine/lista_rutinas/detalle/{id}")
    public String verRutinaDetalle(@PathVariable(name = "id") Long id_rutina, Model model) {
        Routine r = routineRepository.findById(id_rutina).get();
        List<Day> lista_Days = r.getDays();
        List<RoutineDay> listaRD = routineDayServiceImpl.findRoutineDayByRoutineIdAndDayList(id_rutina, lista_Days);

        model.addAttribute("rutina", r);
        model.addAttribute("lista_routine_day", listaRD);
        System.out.println(listaRD);
        return "coach_lista_routines_completo";
    }
    
    @GetMapping("/routine/lista_rutinas/eliminar/{id}")
    public String eliminarRutina(@PathVariable(name = "id") Long id) {
        Routine routine = routineRepository.findById(id).get();

        List<RoutineDay> routineDays = routineDayServiceImpl.findRoutineDayByRoutineIdAndDayList(id, routine.getDays());

        for(int i = 0; i<routineDays.size(); i++) {
            List<Exercise> listaEjercicios = routineDays.get(i).getExercises();
            for(Exercise ex : listaEjercicios) {
                exerciseRepository.delete(ex);
            }
        }

        Long id_user = routine.getUser().getId_user();
        routineRepository.delete(routineRepository.findById(id).get());
        return "redirect:/urbangym/coach/routine/lista_rutinas/" + id_user + "?delete_success";
    }

}
