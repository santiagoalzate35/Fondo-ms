FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

# Crear un grupo y un usuario no root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Establecer el directorio de trabajo
WORKDIR /app

# Crear directorio para la caché y asignar permisos al usuario no root
RUN mkdir -p /cache && \
    chown -R appuser:appgroup /app /cache

# Copiar el archivo JAR a la ubicación de trabajo
COPY applications/app-service/build/libs/E-Commerce-ms.jar /app/E-Commerce-ms.jar

# Configurar las opciones de la JVM (eliminando -Xshareclasses)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

# Cambiar al usuario no root
USER appuser

# Exponer el puerto 8080
EXPOSE 8080

# Definir el punto de entrada de la aplicación
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app/E-Commerce-ms.jar" ]
