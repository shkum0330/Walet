FROM gradle:8.3.0-jdk11 as build
WORKDIR /app

COPY ./backend/account/settings.gradle ./
COPY ./backend/account/build.gradle ./
COPY ./backend/account/src ./src

RUN gradle build -x test --parallel

FROM azul/zulu-openjdk:11
WORKDIR /app

COPY --from=build /app/build/libs/*.jar ./app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]