FROM openjdk:17
ADD target/b2bbase-0.1.jar .
EXPOSE 8050
CMD java -jar
    -DATABASE-IP=${DATABASE-IP}
    -DATABASE-NAME=${DATABASE-NAME}
    -DATABASE-USERNAME=${DATABASE-USERNAME}
    -DATABASE-PASSWORD=${DATABASE-PASSWORD}
    -JWT-SECRET=${JWT-SECRET}
    -EMAIL=${EMAIL}
    -EMAIL-PASSWORD=${EMAIL-PASSWORD}
    -TINYAPI=${TINYAPI}
    -PROFILE=prod
    b2bbase-0.1.jar