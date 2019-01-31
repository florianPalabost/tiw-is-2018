FROM openjdk:8-jre-alpine
COPY ./target/ /usr/src/jwt/
WORKDIR /usr/src/jwt
EXPOSE 8082:8082
CMD ["java", "-jar", "jwt-auth-service-1.0.0.jar"]