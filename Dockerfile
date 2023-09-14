FROM openjdk:17
COPY build/libs/*.jar notification-service.jar
ENTRYPOINT ["java","-jar","/notification-service.jar"]
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar .
ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent.jar"
