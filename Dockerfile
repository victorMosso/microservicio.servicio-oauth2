FROM openjdk:12
VOLUME /tmp
EXPOSE 9010
ADD ./target/springboot.microservicio.oauth-v.1.0.jar oauth.server.jar
ENTRYPOINT [ "java","-jar","oauth.server.jar" ]