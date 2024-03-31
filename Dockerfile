FROM openjdk:20
ADD target/FirstRestProject-spring-boot.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]