FROM ubuntu:22.04
RUN apt update \
    && apt install -y openjdk-17-jre

ADD original-CrosswordGenerator-0.0.1-SNAPSHOT.jar /home/server.jar

WORKDIR /home/

CMD ["java", "-jar", "server.jar"]