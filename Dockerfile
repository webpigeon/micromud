FROM java:openjdk-7

ARG JARFILE=mud-engine-0.0.1-SNAPSHOT-jar-with-dependencies.jar

RUN adduser --disabled-password --gecos "" mud
WORKDIR /home/mud/

# drop root privs
USER mud

# copy the mud into the user's home dir
ADD target/$JARFILE ./bot.jar
CMD ["java", "-jar", "bot.jar"]
