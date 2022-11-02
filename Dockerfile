FROM openjdk:11
EXPOSE 5001
RUN mkdir -p /app/
ADD build/libs/similar-products-backend-1.0.0.jar /app/similar-products-backend-1.0.0.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar", "/app/similar-products-backend-1.0.0.jar"]