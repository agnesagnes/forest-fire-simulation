# Forest fire simulation

### What it does

This project implements a simulation of a forest fire in Vue.js and Java Spring Boot.

The forest is represented by a grid h x l.
The temporal dimension is discretized, the simulation is step by step.
In the initial state, one or several cells are on fire.
If a cell is on fire at step t, at t+1:
- The fire goes out in this cell (the cell is filled with ashes and cannot burn anymore)
- There is a probability p for the fire to spread to each 4 adjacent cells

The simulation stops when there are 0 cells on fire.
The simulation is restarted when the page is reloaded or when the page is accessed.
The Spring Boot server is based on a stateful singleton scope to keep the simulation state across requests for simplicity.


### Project structure

The project contains two sub-folders:
- /client the Vue.js client side 
- /server the Java Spring Boot server side

### Installation and usage

- Inside ./client:

```sh
npm install
npm run dev
```

- Inside ./server:

```sh
mvn clean install
java -jar target/simulation-0.0.1-SNAPSHOT.jar
```

### Configuration file content

The configuration file can on the server side: ./server/src/main/resources/application.yml
It contains the following variables that can be change:
- propagationProbability (between 0 and 1)
- height (of the grid)
- width (of the grid)
- initialFirePositions (array of {row, column} fire positions)

The client expects the server on port 8080, if this is not the case you can change it here: ./client/src/config/configuration.js
