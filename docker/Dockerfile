FROM openjdk:8

ARG JAR_FILE=test-task-0.0.1.jar

ADD ${JAR_FILE} application.jar

EXPOSE 8081

CMD java -jar /application.jar