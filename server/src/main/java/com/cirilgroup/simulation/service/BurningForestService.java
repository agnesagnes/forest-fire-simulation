package com.cirilgroup.simulation.service;

import com.cirilgroup.simulation.configuration.SimulationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BurningForestService {

    private final SimulationConfiguration configuration;
    private int steps;
    private List<Point> firePositions;
    private int[][] burningForest;

    @Autowired
    public BurningForestService(SimulationConfiguration configuration) {
        this.configuration = configuration;
        this.steps = 0;
        initBurningForest();
    }

    public int[][] initializeSimulation() {
        if (steps > 0) {
            initBurningForest();
            steps = 0;
        }
        return burningForest;
    }

    public int[][] nextSimulationStep() {
        steps++;
        final List<Point> newFirePositions = new ArrayList<>();
        for(final Point firePosition : firePositions) {
            final int fireRow = firePosition.row();
            final int fireColumn = firePosition.column();
            if(fireRow > 0) {
                updateFireNeighbor(fireRow-1, fireColumn, newFirePositions);
            }
            if(fireRow < (burningForest.length - 1)) {
                updateFireNeighbor(fireRow + 1, fireColumn, newFirePositions);
            }
            if(fireColumn > 0) {
                updateFireNeighbor(fireRow, fireColumn - 1, newFirePositions);
            }
            if(fireColumn < (burningForest[0].length - 1)) {
                updateFireNeighbor(fireRow, fireColumn + 1, newFirePositions);
            }
            burningForest[fireRow][fireColumn] = FireState.ASHES.ordinal();
        }
        firePositions = newFirePositions;
        return burningForest;
    }

    private void initBurningForest() {
        burningForest = new int[configuration.getHeight()][configuration.getWidth()];
        firePositions = List.copyOf(configuration.getInitialFirePositions());
        for(final Point firePosition : firePositions) {
            burningForest[firePosition.row()][firePosition.column()] = FireState.FIRE.ordinal();
        }
    }

     private void updateFireNeighbor(int row, int column, List<Point> newFirePositions) {
         if (burningForest[row][column] == FireState.NOFIRE.ordinal() && Math.random() < configuration.getPropagationProbability()) {
             burningForest[row][column] = FireState.FIRE.ordinal();
             newFirePositions.add(new Point(row, column));
         }
     }

}
