#!/bin/bash
SIMULATION_NAME=${SIMULATION_NAME:-gatling.test.example.simulation.ExampleSimulation}
JAVA_OPTS="${JAVA_OPTS} --add-opens java.base/java.lang=ALL-UNNAMED --enable-native-access=ALL-UNNAMED -Djava.util.prefs.userRoot=/app/.java"
exec java ${JAVA_OPTS} -cp bin/gatling-java-example.jar  io.gatling.app.Gatling -s ${SIMULATION_NAME} --results-folder results
