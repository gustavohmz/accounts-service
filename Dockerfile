# Imagen base con Java 17
FROM openjdk:17-jdk-slim

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR generado
COPY target/accounts-service-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del microservicio
EXPOSE 8081

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
