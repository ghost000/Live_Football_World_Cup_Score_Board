FROM maven:3.9.9-eclipse-temurin-22 AS build

RUN mkdir -p /app/src

WORKDIR /app/src

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn test

RUN mvn clean package

#EXPOSE 3000

FROM eclipse-temurin:22-jre

WORKDIR /app

COPY --from=build /app/* /app/
#CMD ["java", "-jar", "/target/Live_Football_World_Cup_Score_Board-1.0-SNAPSHOT.jar"]


# Commands:
# clean                         -> docker system prune -a --volumes
# build                         -> docker build --no-cache --rm -t test-maven .
# build with all verbouse infos -> docker build --no-cache --progress=plain --rm -t test-maven .
# run                           -> docker run -it test-maven /bin/bash