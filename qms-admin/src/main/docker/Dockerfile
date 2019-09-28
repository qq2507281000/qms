FROM java:8
MAINTAINER dech(qcloud@dech.io)
VOLUME /tmp
ADD qms-admin.jar app.jar
#RUN bash -c 'touch /app.jar'
#EXPOSE 8761
#ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar $QMS" ]