# Dockerfile

# jdk11 Image Start
FROM openjdk:11

# 인자 설정 - JAR_File
ARG JAR_FILE=build/libs/*.jar

# jar 파일 복제
COPY ${JAR_FILE} DMUIT.jar

# 실행 명령어
ENTRYPOINT ["java", "-jar", "/DMUIT.jar"]

FROM redis:3.0
RUN mkdir /var/log/redis
RUN mkdir /usr/local/etc/redis

COPY conf/redis.conf /usr/local/etc/redis/redis.conf
CMD [ "redis-server", "/usr/local/etc/redis/redis.conf" ]