FROM openjdk:8-jre-alpine

MAINTAINER duanxiaoduan

ENV DUBBO_IP_TO_REGISTRY 192.168.171.209
ENV DUBBO_PORT_TO_REGISTRY 9099

ADD target/demo-0.0.1-SNAPSHOT.jar app.jar

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]

EXPOSE 9099