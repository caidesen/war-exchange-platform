FROM java:8
VOLUME /tmp
ADD /target/item-server-1.0-SNAPSHOT.jar app.jar
EXPOSE 8111
ENTRYPOINT ["java","-jar","/app.jar"]
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime