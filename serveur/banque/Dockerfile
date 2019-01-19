FROM openjdk:8-jre-alpine
COPY ./target/banque-2018.jar /usr/src/banque/
WORKDIR /usr/src/banque
EXPOSE 8081
CMD ["java", "-jar", "banque-2018.jar"]