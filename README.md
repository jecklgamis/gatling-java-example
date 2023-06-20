# gatling-java-example

[![Build](https://github.com/jecklgamis/gatling-java-example/actions/workflows/build.yml/badge.svg)](https://github.com/jecklgamis/gatling-java-example/actions/workflows/build.yml)

An example Gatling Maven project using Java DSL.

## Requirements

* Java 8

## Building

```
mvn clean package
docker build -t gatling-java-example:main .
```

## Running

Using the executable jar file (`run-simulation-using-jar.sh`):

```bash
JAVA_OPTS="-DbaseUrl=http://localhost:8080  -DdurationMin=1 -DrequestPerSecond=10"
SIMULATION_NAME=gatling.test.example.simulation.ExampleSimulation
java ${JAVA_OPTS} -cp target/gatling-java-example.jar io.gatling.app.Gatling -s "${SIMULATION_NAME}"
```

Using the Gatling Maven plugin (`run-simulation-using-plugin.sh`):

```bash
./mvnw -DsimulationClass=gatling.test.example.simulation.ExampleSimulation gatling:test
```

Using the Docker container (`run-simulation-using-docker.sh`):

```bash
docker build -t gatling-java-example:latest .
docker run -e "JAVA_OPTS=-DbaseUrl=http://localhost:8080 -DdurationMin=1 -DrequestPerSecond=10" \
-e SIMULATION_NAME=gatling.test.example.simulation.ExampleSimulation gatling-java-example:latest
```

