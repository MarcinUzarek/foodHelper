FROM openjdk:17.0-jdk
ADD target/foodHelper-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "foodHelper-0.0.1-SNAPSHOT.jar"]