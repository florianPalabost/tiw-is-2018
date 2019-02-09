FROM openjdk:8-jre-alpine
COPY ./target/ /usr/src/banque/
WORKDIR /usr/src/banque
EXPOSE 8082:8082
CMD ["java", "-jar", "banque-2018.jar"]
