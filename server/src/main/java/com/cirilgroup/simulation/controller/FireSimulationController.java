package com.cirilgroup.simulation.controller;

import com.cirilgroup.simulation.service.BurningForestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forest/simulation")
public class FireSimulationController {

    final BurningForestService burningForestService;

    @Autowired
    public FireSimulationController(BurningForestService burningForestService) {
        this.burningForestService = burningForestService;
    }

    @CrossOrigin
    @GetMapping(path = "/initialize")
    public int[][] initializeBurningForest() {
        return burningForestService.initializeSimulation();
    }

    @CrossOrigin
    @GetMapping(path = "/advance")
    public int[][] getForestNextState() {
        return burningForestService.nextSimulationStep();
    }

}
