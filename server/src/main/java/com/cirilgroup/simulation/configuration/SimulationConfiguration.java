package com.cirilgroup.simulation.configuration;

import com.cirilgroup.simulation.service.Point;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "simulation")
public record SimulationConfiguration(double propagationProbability, List<Point> initialFirePositions, int height, int width) {}
