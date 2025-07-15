FROM eclipse-temurin:21-jre-jammy
MAINTAINER Jerrico Gamis <jecklgamis@gmail.com>

RUN apt update -y && apt install -y bash curl && apt clean all && rm -rf /var/lib/apt/lists/*

ENV APP_HOME /app
RUN mkdir -m 0755 -p ${APP_HOME}/bin

COPY target/gatling-java-example.jar ${APP_HOME}/bin/

RUN groupadd -r gatling && useradd -r -ggatling gatling
RUN chown -R gatling:gatling ${APP_HOME}

USER gatling
WORKDIR ${APP_HOME}

COPY docker-entrypoint.sh /
CMD ["/docker-entrypoint.sh"]

