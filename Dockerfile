FROM openjdk:jre-alpine
VOLUME /tmp
ARG JAR_FILE

ENV _JAVA_OPTIONS "-Xms256m -Xmx512m -Djava.awt.headless=true"

COPY ${JAR_FILE} /target/twittertrendingtags.jar

RUN addgroup twittertag && \
    adduser -D -S -h /var/cache/twittertag -s /sbin/nologin -G twittertag twittertag

WORKDIR /opt
USER twittertag
ENTRYPOINT ["spark-submit --packages org.apache.spark:spark-streaming-twitter_2.10:1.5.1 --jars spark-core_2.11-1.5.2.logging.jar ./target/twittertrendingtags.jar Amsterdam"]