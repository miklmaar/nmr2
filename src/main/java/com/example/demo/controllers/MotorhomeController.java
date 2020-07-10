package com.example.demo.controllers;

import com.example.demo.models.Motorhome;
import com.example.demo.repositories.IMotorhomeRepository;
import com.example.demo.repositories.MotorhomeRepoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MotorhomeController {
    private IMotorhomeRepository motorhomeRepository;

    public MotorhomeController() {
        motorhomeRepository = new MotorhomeRepoImpl();
    }

    @GetMapping("/")
    public String homepage(Model model){
        return "index";
    }

    //
    //
    //DISPLAYING ALL MOTORHOMES
    //
    //

    @GetMapping("/motorhomes")
    public String mhpage(Model model){
        indexRead(model);
        return "motorhomes";
    }

    @GetMapping("/deleteMotorhome")
    public String deleteMotorhome(@RequestParam int id, Model model){
        motorhomeRepository.delete(id);
        indexRead(model);
        return "motorhomes";
    }

    @GetMapping("/activateMotorhome")
    public String activateMotorhome(@RequestParam int id, Model model){
        motorhomeRepository.activate(id);
        indexRead(model);
        return "motorhomes";
    }

    //
    //
    //DISPLAY AND UPDATE SINGLE MOTORHOME
    //
    //

    @GetMapping("/motorhome")
    public String getMotorhome(@RequestParam int id, Model model){
        Motorhome motorhome = motorhomeRepository.read(id);
        model.addAttribute("motorhome", motorhome);
        return "motorhome";
    }

    @PostMapping("/motorhomeForm")
    public String updateMotorhome(@ModelAttribute Motorhome motorhome, Model model){
        motorhomeRepository.update(motorhome);
        indexRead(model);
        return "motorhomes";
    }

    //
    //
    //ADDING MOTORHOMES
    //
    //

    @GetMapping("/addMotorhome")
    public String addMotorhome(Model model){
        Motorhome motorhome = new Motorhome();
        model.addAttribute("motorhome", motorhome);
        return "createMotorhome";
    }

    @PostMapping("/addMotorhomeForm")
    public String addingMotorhome(@ModelAttribute Motorhome motorhome, Model model){
        motorhomeRepository.create(motorhome);
        indexRead(model);
        return "motorhomes";
    }


    //
    //
    //Generic method for reading all motorhomes.
    //
    //
    public void indexRead(Model model) {
        model.addAttribute("motorhomes", motorhomeRepository.readAll());
    }
}
