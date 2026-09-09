# gatling-java-example

[![Build and Push](https://github.com/jecklgamis/gatling-java-example/actions/workflows/build.yaml/badge.svg)](https://github.com/jecklgamis/gatling-java-example/actions/workflows/build.yaml)

An example Gatling Maven project using Java DSL.

## Requirements
* Java 25

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

## Running via gatling-server

[gatling-server](https://github.com/jecklgamis/gatling-server) can host and run this simulation remotely - upload
the jar once, then submit runs against it without a local JVM:

```bash
curl -v \
  -H "Authorization: Bearer ${API_TOKEN}" \
  -F "file=@target/gatling-java-example.jar" \
  -F "simulation=gatling.test.example.simulation.ExampleSimulation" \
  -F "javaOpts=-DbaseUrl=http://localhost:8080 -DdurationMin=1 -DrequestPerSecond=10" \
  http://localhost:58080/task/upload
```

See [gatling-server](https://github.com/jecklgamis/gatling-server) for setup, the full API, and how to poll task
status/logs afterward.

### AI Integration

This repo is pre-configured with a project-scoped `.mcp.json` connecting to
[gatling-mcp-server](https://github.com/jecklgamis/gatling-mcp-server), so an MCP-capable AI client (Claude Code,
Claude.ai, Cursor, etc.) can upload the jar and submit/monitor a run just by describing what you want instead of
hand-writing the `curl` command above, e.g.:

> Upload target/gatling-java-example.jar and run gatling.test.example.simulation.ExampleSimulation against
> http://localhost:8080 for 1 minute at 10 requests per second.

Run `claude mcp list` to confirm the connection is active - gatling-mcp-server itself must be running, and
`GATLING_MCP_API_TOKEN` must be set in your shell.


