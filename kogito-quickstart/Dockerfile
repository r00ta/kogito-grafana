#FROM maven:3.6.2-jdk-8 
FROM openjdk:8
COPY app/ /app/
WORKDIR app/
CMD ["./mvnw", "compile", "quarkus:dev"]
#CMD ["sleep", "10000"] 

