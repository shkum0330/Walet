FROM azul/zulu-openjdk:11
WORKDIR /app
COPY /app/build/libs/{domain-name}-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
