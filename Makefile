IMAGE_NAME:=jecklgamis/gatling-java-example
IMAGE_TAG:=$(shell git rev-parse HEAD)

default:
	cat ./Makefile
dist:
	./mvnw clean package
image:
	 docker build -t $(IMAGE_NAME):$(IMAGE_TAG) -t $(IMAGE_NAME):latest .
run-bash:
	 docker run -i -t $(IMAGE_NAME):$(IMAGE_TAG) /bin/bash
run:
	 docker run -e "JAVA_OPTS=-DbaseUrl=http://some-target-host:8080 -DdurationMin=1 -DrequestPerSecond=10" \
 -e SIMULATION_NAME=gatling.test.example.simulation.ExampleSimulation $(IMAGE_NAME):$(IMAGE_TAG)
push:
	docker push $(IMAGE_NAME):$(IMAGE_TAG)
all: dist image


