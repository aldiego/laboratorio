FROM openjdk:11-jre-slim
EXPOSE 8887
ARG JAR_FILE=build/libs/laboratorio-crud.jar
ADD ${JAR_FILE} laboratorio-crud.jar
ENTRYPOINT ["java","-jar","/laboratorio-crud.jar"]