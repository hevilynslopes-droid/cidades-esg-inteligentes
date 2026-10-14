#!/usr/bin/env sh
set -eu
printf '\n== Java ==\n'
java -version
printf '\n== Maven tests ==\n'
./mvnw -B clean verify
printf '\n== Docker Compose validation ==\n'
docker compose config --quiet
printf '\nValidações locais concluídas. Para iniciar os serviços, execute: docker compose up --build -d\n'
