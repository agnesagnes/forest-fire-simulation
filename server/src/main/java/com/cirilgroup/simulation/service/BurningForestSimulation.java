package com.cirilgroup.simulation.service;

import com.cirilgroup.simulation.configuration.SimulationConfiguration;

import java.util.ArrayList;
import java.util.List;

public class BurningForestSimulation {

    private List<Point> firePositions;
    private final int[][] burningForest;
    private final SimulationConfiguration configuration;

    BurningForestSimulation(SimulationConfiguration configuration) {
        this.configuration = configuration;
        burningForest = new int[configuration.height()][configuration.width()];
        firePositions = List.copyOf(configuration.initialFirePositions());
        for(final Point firePosition : firePositions) {
            burningForest[firePosition.row()][firePosition.column()] = FireState.FIRE.ordinal();
        }
    }

    synchronized int[][] nextSimulationStep() {
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

    private void updateFireNeighbor(int row, int column, List<Point> newFirePositions) {
        if (burningForest[row][column] == FireState.NOFIRE.ordinal() && Math.random() < configuration.propagationProbability()) {
            burningForest[row][column] = FireState.FIRE.ordinal();
            newFirePositions.add(new Point(row, column));
        }
    }

    synchronized int[][] getBurningForest() {
        return burningForest;
    }
}
