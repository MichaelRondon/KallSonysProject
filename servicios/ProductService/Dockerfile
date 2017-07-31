FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD target/product-service-1.0.jar product-service-1.0.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /product-service-1.0.jar" ]