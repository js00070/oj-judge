FROM openjdk:8

WORKDIR /app
ADD . .
RUN chmod +x run.sh
RUN javac -cp .:hamcrest-all-1.3.jar:jedis-2.9.0.jar:junit-4.13-beta-2.jar judge/judge.java
ENTRYPOINT ["java", "-cp", ".:hamcrest-all-1.3.jar:jedis-2.9.0.jar:junit-4.13-beta-2.jar","judge.judge"]