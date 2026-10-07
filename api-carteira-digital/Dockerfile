# syntax=docker/dockerfile:1

# ============================================================
# STAGE 1 — BUILD
# ============================================================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace

# Dependencias primeiro: camada invalida so quando o pom.xml muda.
# O cache mount do .m2 nao entra na imagem final, mas evita re-download.
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp dependency:go-offline

# Codigo-fonte + build
COPY src ./src
ARG SKIP_TESTS=true
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp clean package -DskipTests=${SKIP_TESTS}

# Explode o fat jar em camadas (dependencies / spring-boot-loader / application).
# Isso evita guardar o jar aninhado dentro da imagem e permite cache por camada.
RUN java -Djarmode=tools -jar target/*.jar extract \
        --layers --launcher --destination /workspace/layers


# ============================================================
# STAGE 2 — RUNTIME
# ============================================================
FROM eclipse-temurin:21-jre-alpine

# Recebe as flags de JVM por variavel de ambiente, o que mantem
# o ENTRYPOINT em forma exec (sem shell, sinalIZACAO correta no docker stop).
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/urandom -Dfile.encoding=UTF-8"

# Fixa a porta em 8080 dentro do container. SERVER_PORT tem precedencia sobre
# o application-*.yaml, entao a porta e a mesma em dev e prod.
ENV SERVER_PORT=8080

WORKDIR /app

# Usuario sem privilegios
RUN addgroup -S app && adduser -S -G app app \
    && chown -R app:app /app

# Camadas copiadas da mais estavel para a mais volatil (melhor cache)
COPY --from=build --chown=app:app /workspace/layers/dependencies/BOOT-INF/lib/      ./BOOT-INF/lib/
COPY --from=build --chown=app:app /workspace/layers/spring-boot-loader/org/         ./org/
COPY --from=build --chown=app:app /workspace/layers/snapshot-dependencies/          ./
COPY --from=build --chown=app:app /workspace/layers/application/BOOT-INF/classes/   ./BOOT-INF/classes/
COPY --from=build --chown=app:app /workspace/layers/application/META-INF/           ./META-INF/

USER app

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=45s --retries=3 \
    CMD wget -qO- "http://localhost:${SERVER_PORT:-8080}/health" >/dev/null 2>&1 || exit 1

ENTRYPOINT ["java", "-cp", ".", "org.springframework.boot.loader.launch.JarLauncher"]