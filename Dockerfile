FROM openjdk:17-oracle
ADD target/foodHelper-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar foodHelper-0.0.1-SNAPSHOT.jar