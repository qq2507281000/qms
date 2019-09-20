FROM openjdk:8u171-jdk-alpine
RUN apk add --no-cache tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone && apk del tzdata
WORKDIR /app
VOLUME /app/upload
VOLUME /data
COPY ./target/*.jar /app/app.jar
COPY ./app.conf /app/app.conf
ENV SPRING_PROFILES_ACTIVE=dev,swagger
EXPOSE 8080
CMD java -jar /app/app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}

