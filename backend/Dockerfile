FROM openjdk:21-jdk
ARG JAR_FILE=target/
COPY ${JAR_FILE} admin_landsurveys-0.0.1.jar
EXPOSE ${JAR_FILE} admin_landsurveys.jar
ENTRYPOINT ["java", "-jar", "app_eureka.jar"]
EXPOSE 3500:3500