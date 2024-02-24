FROM openjdk:17
RUN mkdir -p /app/data
WORKDIR /app
COPY target/b2bbase-0.1.jar /app
EXPOSE 8080
CMD ["sh", "-c", "java -jar \
  -DDATABASE_IP=$DATABASE_IP \
  -DDATABASE_NAME=$DATABASE_NAME \
  -DDATABASE_USERNAME=$DATABASE_USERNAME \
  -DDATABASE_PASSWORD=$DATABASE_PASSWORD \
  -DJWT_SECRET=$JWT_SECRET \
  -DEMAIL=$EMAIL \
  -DEMAIL_PASSWORD=$EMAIL_PASSWORD \
  -DTINYAPI=$TINYAPI \
  -DPROFILE=prod /app/b2bbase-0.1.jar"]