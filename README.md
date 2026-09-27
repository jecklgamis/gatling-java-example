# gatling-java-example

[![Build and Push](https://github.com/jecklgamis/gatling-java-example/actions/workflows/build.yaml/badge.svg)](https://github.com/jecklgamis/gatling-java-example/actions/workflows/build.yaml)

An example Gatling Maven project using Java DSL.

No server to test against? Point `baseUrl` at [http-sink](https://github.com/jecklgamis/http-sink) — run it locally
(`docker run --name http-sink -p 38080:38080 -it jecklgamis/http-sink:main`) or use the live instance at
https://http-sink.jecklgamis.com.

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

## Running Using Gatling Server

[gatling-server](https://github.com/jecklgamis/gatling-server) can host and run this simulation remotely, without
a local JVM. Start it first:

```bash
docker run -it --name gatling-server -p 58080:58080 -e API_TOKEN=some-secret-token jecklgamis/gatling-server:main
```

### Submit a Task

Build the jar (`./mvnw clean package`), then submit it one of two ways.

**Upload the jar and run it in one call:**

```bash
curl -v \
  -H "Authorization: Bearer some-secret-token" \
  -F "file=@target/gatling-java-example.jar" \
  -F "simulation=gatling.test.example.simulation.ExampleSimulation" \
  -F "javaOpts=-DbaseUrl=http://localhost:8080 -DdurationMin=1 -DrequestPerSecond=10" \
  http://localhost:58080/task/upload
```

**Or, if the jar is already reachable via an http(s)/S3 URL, submit by reference instead:**

```bash
curl -v \
  -H "Authorization: Bearer some-secret-token" \
  -H "Content-Type: application/json" \
  -d '{
        "simulation": "gatling.test.example.simulation.ExampleSimulation",
        "javaOpts": "-DbaseUrl=http://localhost:8080 -DdurationMin=1 -DrequestPerSecond=10",
        "url": "https://example.com/gatling-java-example.jar"
      }' \
  http://localhost:58080/task/submit
```

Both return a `taskId`.

### Check Status and Results

```bash
# Runtime status
curl -H "Authorization: Bearer some-secret-token" http://localhost:58080/task/{taskId}

# Console log / Gatling's simulation log / results archive
curl -H "Authorization: Bearer some-secret-token" http://localhost:58080/task/console/{taskId}
curl -H "Authorization: Bearer some-secret-token" http://localhost:58080/task/simulationLog/{taskId}
curl -H "Authorization: Bearer some-secret-token" http://localhost:58080/task/results/{taskId} -o results.tar.gz
```

Or browse the raw task workspace (console log, Gatling report, simulation log) in a browser at
`http://localhost:58080/workspace/{taskId}/` - protected by HTTP Basic Auth (`default`/`default` unless
`BROWSE_USERNAME`/`BROWSE_PASSWORD` were set).

See the [gatling-server docs](https://jecklgamis.github.io/gatling-server/) for the full API reference, deployment
options, and AI integration.

### AI Integration

This repo is pre-configured with a project-scoped `.mcp.json` connecting to
[gatling-mcp-server](https://github.com/jecklgamis/gatling-mcp-server), so an MCP-capable AI client (Claude Code,
Claude.ai, Cursor, etc.) can upload the jar and submit/monitor a run just by describing what you want instead of
hand-writing the `curl` command above, e.g.:

> Upload target/gatling-java-example.jar and run gatling.test.example.simulation.ExampleSimulation against
> http://localhost:8080 for 1 minute at 10 requests per second.

Run `claude mcp list` to confirm the connection is active - gatling-mcp-server itself must be running, and
`GATLING_MCP_API_TOKEN` must be set in your shell.


