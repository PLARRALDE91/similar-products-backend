FROM openjdk:11
EXPOSE 5001
RUN mkdir -p /app/
ADD build/libs/similar-products-backend-0.0.2.jar /app/similar-products-backend-0.0.2.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar", "/app/similar-products-backend-0.0.2.jar"]