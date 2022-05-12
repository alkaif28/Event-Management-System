FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY target/eventtex-application-0.0.1-SNAPSHOT.jar backend.jar
ENV MYSQL_HOST=localhost
ENV MYSQL_DATABASE=eventtex
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_PORT=3306
EXPOSE 9898
CMD ["java","-jar","/backend.jar"]