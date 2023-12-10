package com.app.urbangym.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.urbangym.entitys.Plan;
import com.app.urbangym.repository.PlanRepository;
import com.app.urbangym.service.PlanServiceImpl;

// Solo lo debe ver el admin controlando los 'plan'
// Muestra form del plan, lo guarda, muestra la foto del plan cuando se liste
@Controller
@RequestMapping("/urbangym/admin/plan")
public class AdminPlanController {

    @Autowired
    @Qualifier("planRepository")
    private PlanRepository planRepository;

    @Autowired
    private PlanServiceImpl planServiceImpl;

    // Listar los planes, ver, editar, eliminar
    @GetMapping("/lista")
    public String verListaDePlanes(Model model, @Param("keyWord") String keyWord) {
        model.addAttribute("lista_planes", planServiceImpl.listAll(keyWord));
        model.addAttribute("keyWord", keyWord);
        return "list_plan";
    }

    // Muestra el form de los planes
    @GetMapping("/form")
    public String verFormularioPlan(Model model) {

        model.addAttribute("titulo", "Añadir nuevo PLAN");
        model.addAttribute("plan", new Plan());

        return "form_plan";
    }

    // Guarda los planes
    @PostMapping("/form/guardar")
    public String guardarPlan(@ModelAttribute("plan") @Valid Plan plan, BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile file, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Añadir nuevo PLAN");
            return "form_plan";
        }

        Optional<Plan> planOptional = planRepository.findById(plan.getId());

        if (planOptional.isPresent()) {

            Plan planExistente = planOptional.get();

            if (!file.isEmpty()) {
                try {
                    plan.setPhoto(file.getBytes());
                } catch (Exception e) {

                }
            } else {
                plan.setPhoto(planExistente.getPhoto());
            }

        } else {
            if (!file.isEmpty()) {
                try {
                    plan.setPhoto(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    File defaultImage = new File("src/main/resources/static/images/card1.jpg");
                    byte[] defaultImageBytes = Files.readAllBytes(defaultImage.toPath());
                    plan.setPhoto(defaultImageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        model.addAttribute("titulo", "Añadir nuevo PLAN");
        model.addAttribute("plan", new Plan());
        planRepository.save(plan);
        return "redirect:/urbangym/admin/plan/form?save_success";
    }

    @PostMapping("/form/editar")
    public String guardarEditarPlan(@ModelAttribute("coach") @Valid Plan plan, BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile file, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar PLAN");
            return "form_plan_edit";
        }

        Optional<Plan> planOptional = planRepository.findById(plan.getId());

        if (planOptional.isPresent()) {

            Plan planExistente = planOptional.get();

            if (!file.isEmpty()) {
                try {
                    plan.setPhoto(file.getBytes());
                } catch (Exception e) {

                }
            } else {
                plan.setPhoto(planExistente.getPhoto());
            }

        } else {
            if (!file.isEmpty()) {
                try {
                    plan.setPhoto(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    File defaultImage = new File("src/main/resources/static/images/card1.jpg");
                    byte[] defaultImageBytes = Files.readAllBytes(defaultImage.toPath());
                    plan.setPhoto(defaultImageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        model.addAttribute("titulo", "Editar PLAN");
        model.addAttribute("plan", new Plan());
        planRepository.save(plan);
        return "redirect:/urbangym/admin/plan/editar/" + plan.getId() + "?edit_success";
    }

    // Aun no esta en uso, debe usarse para listar los planes y que el admin pueda
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

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarPlan(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("plan", planRepository.findById(id));
        model.addAttribute("titulo", "Editar plan");
        return "form_plan_edit";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPlan(Model model, @PathVariable(name = "id") Long id) {
        planRepository.delete(planRepository.findById(id).get());
        return "redirect:/urbangym/admin/plan/lista?delete_success";
    }

}
