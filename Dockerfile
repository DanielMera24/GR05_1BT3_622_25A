FROM tomcat:10-jre17

# Eliminar aplicaciones predeterminadas de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Crear directorio para datos persistentes
RUN mkdir -p /usr/local/tomcat/data

# Instalar SQLite
RUN apt-get update && \
    apt-get install -y sqlite3 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Variables de entorno para la aplicación
ENV DB_PATH=/usr/local/tomcat/data/GestorTorneos.db

# Copiar el archivo WAR a la carpeta webapps
COPY target/GestorFutbol.war /usr/local/tomcat/webapps/ROOT.war

# Copiar configuración personalizada de Tomcat (opcional)
# COPY context.xml /usr/local/tomcat/conf/context.xml

# Exponer el puerto de Tomcat
EXPOSE 8080

# Punto de entrada de Tomcat
CMD ["catalina.sh", "run"]