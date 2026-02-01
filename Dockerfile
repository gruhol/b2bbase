FROM eclipse-temurin:21-jdk-alpine
RUN mkdir -p /app/data
WORKDIR /app
COPY target/b2bbase-1.0.jar /app/app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]