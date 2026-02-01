FROM eclipse-temurin:21-jdk-alpine
RUN mkdir -p /app/data
WORKDIR /app
COPY target/b2bbase-0.1.jar /app
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]