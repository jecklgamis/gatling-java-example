# gatling-java-example

[![Build and Push](https://github.com/jecklgamis/gatling-java-example/actions/workflows/build.yaml/badge.svg)](https://github.com/jecklgamis/gatling-java-example/actions/workflows/build.yaml)

An example Gatling Maven project using Java DSL.

## Requirements
* Java 21

## Building

```
./mvnw clean package
```

## Running

Using the executable jar file (`run-simulation-using-jar.sh`):

```bash
JAVA_OPTS="-DbaseUrl=http://localhost:8080  -DdurationMin=1 -DrequestPerSecond=10"
SIMULATION_NAME=gatling.test.example.simulation.ExampleSimulation
java ${JAVA_OPTS} -cp target/gatling-java-example.jar io.gatling.app.Gatling --simulation "${SIMULATION_NAME}" --results-folder results
```

Using the Gatling Maven plugin (`run-simulation-using-plugin.sh`):

```bash
./mvnw -DsimulationClass=gatling.test.example.simulation.ExampleSimulation gatling:test
```

Using the Docker container (`run-simulation-using-docker.sh`):

```bash
./mvnw clean package
docker build -t gatling-java-example:main .
docker run -e "JAVA_OPTS=-DbaseUrl=http://localhost:8080 -DdurationMin=1 -DrequestPerSecond=10" \
-e SIMULATION_NAME=gatling.test.example.simulation.ExampleSimulation gatling-java-example:main
```

As Kubernetes Job:
* Ensure you have [Helm](https://helm.sh/) installed and can deploy to a Kubernetes cluster locally.
```bash
cd deployment/k8s/helm
make package install
```



