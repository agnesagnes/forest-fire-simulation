package com.cirilgroup.simulation.configuration;

import com.cirilgroup.simulation.service.Point;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "simulation")
@Configuration
public class SimulationConfiguration {

    private double propagationProbability;
    private List<Point> initialFirePositions;
    private int height;
    private int width;

    public double getPropagationProbability() {
        return propagationProbability;
    }

    public List<Point> getInitialFirePositions() {
        return initialFirePositions;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setPropagationProbability(double propagationProbability) {
        this.propagationProbability = propagationProbability;
    }

    public void setInitialFirePositions(List<Point> initialFirePositions) {
        this.initialFirePositions = initialFirePositions;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
