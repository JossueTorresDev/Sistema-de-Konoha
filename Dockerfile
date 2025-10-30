# ===========================
# 🧩 Etapa 1: Build con Maven
# ===========================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml y resolver dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar sin tests
RUN mvn clean package -DskipTests

# ===========================
# 🚀 Etapa 2: Imagen final
# ===========================
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar el JAR generado
COPY --from=build /app/target/*.jar app.jar

# Puerto expuesto
EXPOSE 5004

ENTRYPOINT ["java", "-jar", "app.jar"]
