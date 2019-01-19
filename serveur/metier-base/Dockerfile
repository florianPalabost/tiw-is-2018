FROM openjdk:8-jre-alpine
COPY ./target/metier-base-2018.jar /usr/src/metier-base/
WORKDIR /usr/src/metier-base
EXPOSE 8081
CMD ["java", "-jar", "metier-base-2018.jar"]