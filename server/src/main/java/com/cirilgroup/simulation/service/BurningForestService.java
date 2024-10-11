package com.cirilgroup.simulation.service;

import com.cirilgroup.simulation.configuration.SimulationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BurningForestService {

    private final SimulationConfiguration configuration;
    private BurningForestSimulation burningForestSimulation;

    @Autowired
    public BurningForestService(SimulationConfiguration configuration) {
        this.configuration = configuration;
        this.burningForestSimulation = new BurningForestSimulation(configuration);
    }

    public int[][] initializeSimulation() {
        burningForestSimulation = new BurningForestSimulation(configuration);
        return burningForestSimulation.getBurningForest();
    }

    public int[][] nextSimulationStep() {
        return burningForestSimulation.nextSimulationStep();
    }

}
