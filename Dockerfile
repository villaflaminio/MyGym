FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 4004
ENTRYPOINT ["java","-jar","/app.jar"]