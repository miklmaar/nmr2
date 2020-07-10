package com.example.demo.controllers;

import com.example.demo.models.Motorhome;
import com.example.demo.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;

@Controller
public class MaintenanceController {
    private IMotorhomeRepository motorhomeRepository;
    private IMaintenanceRepository cleanRepository;
    private IMaintenanceRepository repairRepository;

    public MaintenanceController(){
        motorhomeRepository = new MotorhomeRepoImpl();
        cleanRepository = new CleanRepoImpl();
        repairRepository = new RepairRepoImpl();
    }

    @GetMapping("/cleanrepair")
    public String maintenance(Model model){
        LinkedList<Motorhome> repairList = repairRepository.readList();
        LinkedList<Motorhome> cleanList = cleanRepository.readList();

        model.addAttribute("cleanListSize", cleanList.size());

        if(cleanList.size() != 0){
            Motorhome cleanMotorhome = motorhomeRepository.read(cleanList.getFirst().getMotorhomeID());
            model.addAttribute("nextToClean", cleanMotorhome);
        }

        model.addAttribute("repListSize", repairList.size());

        if(repairList.size() != 0){
            Motorhome repairMotorhome = motorhomeRepository.read(repairList.getFirst().getMotorhomeID());
            model.addAttribute("nextToRepair", repairMotorhome);
        }

        model.addAttribute("cleanQueue", cleanList);
        model.addAttribute("repairQueue", repairList);

        return "Maintenance";
    }

    @GetMapping("/nextcleaned")
    public String nextCleaned(Model model){
        cleanRepository.popFromQueue();

        LinkedList<Motorhome> repairList = repairRepository.readList();
        LinkedList<Motorhome> cleanList = cleanRepository.readList();

        model.addAttribute("cleanListSize", cleanList.size());

        if(cleanList.size() != 0){
            Motorhome cleanMotorhome = motorhomeRepository.read(cleanList.getFirst().getMotorhomeID());
            model.addAttribute("nextToClean", cleanMotorhome);
        }

        model.addAttribute("repListSize", repairList.size());

        if(repairList.size() != 0){
            Motorhome repairMotorhome = motorhomeRepository.read(repairList.getFirst().getMotorhomeID());
            model.addAttribute("nextToRepair", repairMotorhome);
        }

        model.addAttribute("cleanQueue", cleanList);
        model.addAttribute("repairQueue", repairList);

        return "Maintenance";
    }

    @GetMapping("/nextrepaired")
    public String nextRepaired(Model model){
        repairRepository.popFromQueue();

        LinkedList<Motorhome> repairList = repairRepository.readList();
        LinkedList<Motorhome> cleanList = cleanRepository.readList();

        model.addAttribute("cleanListSize", cleanList.size());

        if(cleanList.size() != 0){
            Motorhome cleanMotorhome = motorhomeRepository.read(cleanList.getFirst().getMotorhomeID());
            model.addAttribute("nextToClean", cleanMotorhome);
        }

        model.addAttribute("repListSize", repairList.size());

        if(repairList.size() != 0){
            Motorhome repairMotorhome = motorhomeRepository.read(repairList.getFirst().getMotorhomeID());
            model.addAttribute("nextToRepair", repairMotorhome);
        }

        model.addAttribute("cleanQueue", cleanList);
        model.addAttribute("repairQueue", repairList);

        return "Maintenance";
    }

}
