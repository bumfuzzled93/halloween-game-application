FROM openjdk:17-jdk-alpine
COPY build/libs/halloween-game-application-0.0.1-SNAPSHOT.jar halloween-game-application-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/halloween-game-application-0.0.1-SNAPSHOT.jar"]