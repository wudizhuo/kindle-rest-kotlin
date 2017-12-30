FROM openjdk:8-jdk-alpine

RUN apk add --update bash && rm -rf /var/cache/apk/*

RUN apk add --no-cache curl && rm -rf /var/cache/apk/*

# Install kindlegen
ENV KINDLEGEN_VERSION=kindlegen_linux_2.6_i386_v2_9
ENV PATH "$PATH:/usr/local/bin"

RUN mkdir -p /tmp/kindlegen && \
	cd /tmp/kindlegen && \
	curl http://kindlegen.s3.amazonaws.com/${KINDLEGEN_VERSION}.tar.gz -o kindlegen.tar.gz && \
	tar xvf kindlegen.tar.gz && \
	chown root. kindlegen && \
	chmod 755 kindlegen && \
	mv kindlegen /usr/local/bin/ && \
	rm -rf /tmp/kindlegen

VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
