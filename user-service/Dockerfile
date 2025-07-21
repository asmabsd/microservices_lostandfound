FROM openjdk:17
EXPOSE 8081
ADD target/user-service-0.0.1-SNAPSHOT.jar userservice.jar
ENTRYPOINT ["java","-jar","userservice.jar"]
