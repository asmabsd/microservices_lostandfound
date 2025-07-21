FROM openjdk:17
EXPOSE 8761
ADD target/service-registry-0.0.1-SNAPSHOT.jar serviceregistry.jar
ENTRYPOINT ["java","-jar","serviceregistry.jar"]
