FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD target/product-service-0.0.5.jar product-service.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /product-service.jar" ]