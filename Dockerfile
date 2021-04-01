FROM adoptopenjdk/openjdk11:alpine-slim

ENV DATA_DIR=/images
ENV DB_HOST=host.docker.intenal

RUN addgroup -S spring && adduser -S spring -G spring

RUN mkdir -p $DATA_DIR
RUN chown spring $DATA_DIR

USER spring:spring
ARG JAR_FILE=target/api-holasend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
