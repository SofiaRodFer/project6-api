# --- ESTÁGIO 1: Build (Compilação) ---
# Alterado para usar a imagem do JDK 21
FROM eclipse-temurin:21-jdk-jammy AS build

# Define o diretório de trabalho dentro do container
WORKDIR /workspace/app

# Copia os arquivos de build (Gradle) para aproveitar o cache do Docker
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copia o código-fonte da aplicação
COPY src src

# Comando para o Gradle construir o projeto e gerar o .jar executável
# O --no-daemon é recomendado para ambientes de CI/CD e Docker
RUN ./gradlew bootJar --no-daemon


# --- ESTÁGIO 2: Run (Execução) ---
# Alterado para usar a imagem do JRE 21, que é bem menor
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o .jar gerado no estágio de build para a imagem final
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# Expõe a porta 8080, que é a porta padrão do Spring Boot
EXPOSE 8080

# Comando que será executado quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]