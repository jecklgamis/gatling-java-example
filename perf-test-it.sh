#!/usr/bin/env bash
java -cp target/gatling-java-example.jar io.gatling.app.Gatling -s gatling.test.example.simulation.ExampleSimulation
