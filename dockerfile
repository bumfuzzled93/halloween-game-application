FROM openjdk:17-alpine

COPY build/libs/halloween-game-application-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java"]

CMD ["-jar", "/app/app.jar"]

EXPOSE 8080

RUN apk --update --no-cache add curl

#HEALTHCHECK CMD curl -f http://localhost:8080/health || exit 1