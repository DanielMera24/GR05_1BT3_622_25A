# Usar una imagen específica de Tomcat con versión exacta
FROM tomcat:10.0.27-jre17-temurin

# Configurar locale para evitar problemas de caracteres
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

# Eliminar aplicaciones predeterminadas de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Crear directorio para datos persistentes con permisos adecuados
RUN mkdir -p /usr/local/tomcat/data && \
    chmod 755 /usr/local/tomcat/data

# Instalar dependencias necesarias (incluyendo SQLite)
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    sqlite3 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Variables de entorno para la aplicación
ENV DB_PATH=/usr/local/tomcat/data/GestorTorneos.db

# Copiar el archivo WAR con permisos adecuados
COPY target/GestorFutbol.war /usr/local/tomcat/webapps/ROOT.war

# Configuración de timezone (importante para fechas)
ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Exponer el puerto de Tomcat
EXPOSE 8080

# Usar exec form para el CMD
CMD ["catalina.sh", "run"]