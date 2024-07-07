# Use uma imagem base do JDK 11
FROM openjdk:17-alpine

# Crie um diretório para o aplicativo
WORKDIR /app

# Copie o jar do aplicativo para o diretório de trabalho
COPY target/hwa-stats-analyzer-0.0.1-SNAPSHOT.jar /app/hwa-stats-analyzer.jar

# Exponha a porta que o Spring Boot usará
EXPOSE 8080

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "hwa-stats-analyzer.jar"]
