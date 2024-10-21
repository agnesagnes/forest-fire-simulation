package com.cirilgroup.simulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FireSimulationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FireSimulationApplication.class, args);
	}

}
