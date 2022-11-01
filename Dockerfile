FROM openjdk:11
EXPOSE 5001
RUN mkdir -p /app/
ADD build/libs/similar-products-backend-0.0.1-SNAPSHOT.jar /app/similar-products-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/similar-products-backend-0.0.1-SNAPSHOT.jar"]