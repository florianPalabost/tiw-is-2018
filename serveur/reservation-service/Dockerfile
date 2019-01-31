FROM openjdk:8-jre-alpine
COPY ./target/ /usr/src/reservations/
WORKDIR /usr/src/reservations
EXPOSE 8081:8081
CMD ["java", "-jar", "reservation-2018.jar"]