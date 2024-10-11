package com.cirilgroup.simulation.service;

import com.cirilgroup.simulation.configuration.SimulationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BurningForestSimulation {

    private int steps;
    private List<Point> firePositions;
    private final int[][] burningForest;
    private final AtomicBoolean isProcessing;
    private final SimulationConfiguration configuration;

    BurningForestSimulation(SimulationConfiguration configuration) {
        this.isProcessing = new AtomicBoolean();
        this.configuration = configuration;
        this.steps = 0;
        burningForest = new int[configuration.getHeight()][configuration.getWidth()];
        firePositions = List.copyOf(configuration.getInitialFirePositions());
        for(final Point firePosition : firePositions) {
            burningForest[firePosition.row()][firePosition.column()] = FireState.FIRE.ordinal();
        }
    }

    int[][] nextSimulationStep() {
        if(!isProcessing.compareAndSet(false, true)) {
            return burningForest;
        }
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
        isProcessing.set(false);
        return burningForest;
    }

    private void updateFireNeighbor(int row, int column, List<Point> newFirePositions) {
        if (burningForest[row][column] == FireState.NOFIRE.ordinal() && Math.random() < configuration.getPropagationProbability()) {
            burningForest[row][column] = FireState.FIRE.ordinal();
            newFirePositions.add(new Point(row, column));
        }
    }

    int[][] getBurningForest() {
        return burningForest;
    }
}
