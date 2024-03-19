FROM maven:3.8.6-openjdk-11

WORKDIR /iTrust2
COPY . .
RUN mvn clean package -DskipTests
CMD mvn spring-boot:run
