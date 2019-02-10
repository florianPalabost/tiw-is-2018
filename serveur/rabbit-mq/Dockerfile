FROM openjdk:8-jre-alpine
COPY ./target/ /usr/src/rabbitmq/
WORKDIR /usr/src/rabbitmq/
CMD ["java", "-jar", "rabbitmq-2018.jar"]
