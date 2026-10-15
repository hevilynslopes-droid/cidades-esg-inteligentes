# Cidades ESG Inteligentes — Carbono API

API REST para apoiar a gestão de sustentabilidade, emissões de carbono, compensações e planos de redução. Este pacote prepara a aplicação existente para práticas DevOps, com build/testes automatizados, containerização e configuração de CI/CD.

> **Estado da entrega:** código, Docker/Compose, workflow e documentação estão preparados. O deploy em staging e produção só será considerado concluído após configurar os servidores/secrets e executar os workflows com sucesso. Este repositório não inclui screenshots fictícios nem afirma que ambientes externos já estejam publicados.

## 1. Tecnologias

- Java 21 e Spring Boot 3.5.x
- Maven
- Oracle Database Free e Flyway
- Spring Security e JWT
- JUnit 5 / Spring Boot Test, com perfil de teste usando H2 em memória
- Docker e Docker Compose
- GitHub Actions e GitHub Container Registry (GHCR)
- Insomnia para testes manuais de API (`docs/ESG-Carbon-API-Insomnia.yaml`)

## 2. Estrutura do projeto

```text
carbono-api/
├── .github/workflows/ci-cd.yml
├── deploy/docker-compose.deploy.yml
├── docs/ESG-Carbon-API-Insomnia.yaml
├── scripts/check-local.sh
├── src/main/java/              # controllers, services, repositories, entities, DTOs
├── src/main/resources/         # application.yaml e migrações Flyway
├── src/test/java/              # testes automatizados
├── src/test/resources/         # perfil de testes isolado
├── .env.example
├── .dockerignore
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 3. Executar localmente com Docker

### Pré-requisitos

- Docker Desktop / Docker Engine com Docker Compose v2
- Git (opcional)
- Java 21 e Maven Wrapper para executar testes fora do container

### Preparar ambiente

1. Copie `.env.example` para `.env`.
2. Troque **todos** os valores de exemplo por credenciais fortes e únicas. O segredo JWT deve conter pelo menos 32 bytes aleatórios.
3. Não versione o arquivo `.env`. Ele está incluído no `.gitignore`.

Linux/macOS:

```bash
cp .env.example .env
# edite .env antes de continuar
```

PowerShell:

```powershell
Copy-Item .env.example .env
# edite .env antes de continuar
```

Gere um segredo JWT, por exemplo, com Python:

```bash
python -c "import secrets; print(secrets.token_hex(32))"
```

### Build e inicialização

```bash
docker compose config
docker compose up --build -d
docker compose ps
docker compose logs -f carbono-api
```

A API escuta na porta `8080` e usa o context path `/api/v1`. O Oracle é exposto apenas em `127.0.0.1:1521` no ambiente local. Na primeira inicialização, o Oracle pode levar alguns minutos para ficar saudável e executar as migrações Flyway.

Parar os containers:

```bash
docker compose down
```

Apagar também os dados persistidos do banco (**operação destrutiva**):

```bash
docker compose down -v
```

## 4. Build e testes sem Docker

Com Java 21 instalado:

```bash
./mvnw -B clean verify
```

No Windows:

```powershell
.\mvnw.cmd -B clean verify
```

O perfil `test` usa H2 em memória e desabilita Flyway para que o teste de contexto não dependa de uma instância Oracle. Isso não substitui testes de integração contra Oracle; esses testes devem ser executados separadamente antes de uma publicação de produção.

## 5. CI/CD com GitHub Actions

Workflow: [`.github/workflows/ci-cd.yml`](.github/workflows/ci-cd.yml). O workflow executa build/testes, publica a imagem no GHCR e mantém os deploys condicionados à configuração explícita dos ambientes.

1. **Checkout:** obtém a revisão do repositório.
2. **Setup Java:** configura Java 21 e cache Maven.
3. **Build/teste:** executa `mvn -B clean verify`; em falha, as etapas seguintes dependentes não avançam.
4. **Relatório:** publica os relatórios Surefire como artefato do workflow.
5. **Imagem:** após build/testes bem-sucedidos, constrói a imagem e publica tags com o SHA do commit e `latest` no GHCR (não roda em pull request).
6. **Staging:** em push na branch `main`, o deploy só é habilitado quando a variável de repositório `ENABLE_STAGING_DEPLOY` está definida como `true`, além dos secrets e do servidor estarem configurados.
7. **Produção:** deploy manual por `workflow_dispatch`, selecionando a opção `deploy_production`, habilitado somente quando `ENABLE_PRODUCTION_DEPLOY=true`; configure aprovação obrigatória no environment `production`.

### Secrets necessários

Crie os seguintes secrets nas configurações do environment GitHub correspondente. `GHCR_READ_TOKEN` deve permitir leitura da imagem no GHCR para o servidor remoto. Ative os deploys somente depois de configurar os hosts. Detalhes em [`docs/CONFIGURACAO_GITHUB_E_DEPLOY.md`](docs/CONFIGURACAO_GITHUB_E_DEPLOY.md). Não coloque valores reais no YAML.

| Ambiente | Secrets |
|---|---|
| `staging` | `STAGING_HOST`, `STAGING_USER`, `STAGING_SSH_KEY`, `STAGING_PATH`, `GHCR_READ_TOKEN` |
| `production` | `PRODUCTION_HOST`, `PRODUCTION_USER`, `PRODUCTION_SSH_KEY`, `PRODUCTION_PATH`, `GHCR_READ_TOKEN` |

O workflow usa `GITHUB_TOKEN` para publicar a imagem no GHCR. Além dos secrets por ambiente, configure em **Settings → Secrets and variables → Actions → Variables** as variáveis `ENABLE_STAGING_DEPLOY=true` e/ou `ENABLE_PRODUCTION_DEPLOY=true` somente depois de preparar o respectivo servidor. O servidor remoto precisa ter Docker Engine, Compose v2, `curl`, diretório de deploy gravável pelo usuário SSH e arquivo `.env` local com `ORACLE_PASSWORD`, `APP_USER`, `APP_USER_PASSWORD` e `JWT_SECRET`. O usuário remoto precisa ter permissão para executar Docker. Mantenha staging e produção em hosts/dados separados.

> A etapa de staging está associada a push na branch `main`. Se o repositório usar `master`, ajuste a condição do job ou padronize a branch para `main`. A produção é disparada manualmente e deve ser protegida por aprovação do ambiente.

## 6. Dockerfile e arquitetura

O Dockerfile usa duas etapas:

- **Build:** Maven + Temurin 21, resolve dependências e executa `mvn clean verify`.
- **Runtime:** imagem JRE, copia somente o JAR final, expõe a porta 8080 e executa como usuário não-root `appuser`.

O Compose local declara:

- `oracle-db`: Oracle Free, healthcheck, volume persistente `oracle_data`.
- `carbono-api`: API Java, variáveis de ambiente e dependência condicionada à saúde do banco.
- `esg_network`: rede bridge privada para comunicação entre os containers.

Para staging/produção, use `deploy/docker-compose.deploy.yml`, que recebe a imagem versionada pelo workflow. Os serviços não devem expor a porta do Oracle publicamente. Coloque a API atrás de um proxy reverso com TLS em ambientes acessíveis externamente.

## 7. Testes e validação

Validação local automatizada:

```bash
./scripts/check-local.sh
```

O script exige Java, Maven Wrapper e Docker. A coleção Insomnia em `docs/` pode ser importada para validar endpoints manualmente. Use contas e dados de teste, nunca tokens/credenciais reais em capturas de tela.

Evidências a registrar após executar o projeto:

- log do job `build-test` com build e testes aprovados;
- log de publicação da imagem GHCR com tag SHA;
- `docker compose ps` no staging e verificação funcional da API;
- execução de produção aprovada e verificação pós-deploy;
- capturas do Insomnia com tokens, e-mails e dados sensíveis ocultos.

## 8. Segurança e operação

- Não versionar `.env`, senhas, chaves privadas nem tokens.
- Rotacionar imediatamente credenciais que já tenham sido publicadas anteriormente.
- Usar segredos diferentes por ambiente e um JWT secret forte e aleatório.
- Restringir portas de banco à rede privada e expor a API somente via HTTPS em ambientes externos.
- Configurar backup e restauração testada para o volume Oracle.
- Definir política de retenção e atualização de imagens base/dependências.
- Considerar scan de vulnerabilidades de dependências e imagens como melhoria adicional.

## 9. Limitações conhecidas / próximos passos

- O pipeline e os manifests foram adicionados, mas o deploy real exige infraestrutura e secrets fornecidos pelo responsável pelo projeto.
- O teste existente verifica carregamento do contexto. Ampliar cobertura de unidade e integração é recomendado.
- A compatibilidade real de todas as migrações com a imagem Oracle Free deve ser validada em execução limpa.
- Configurar domínio, TLS, backups, monitoramento e política de rollback no ambiente alvo.

## 10. Checklist de entrega FIAP

- [x] ZIP com código-fonte da aplicação.
- [x] `Dockerfile` incluído.
- [x] `docker-compose.yml` incluído com volume, variáveis e rede.
- [x] Workflow de CI/CD incluído.
- [x] Testes executados como etapa obrigatória do build.
- [x] `.env.example` e `.gitignore` para proteger segredos.
- [x] README com execução, pipeline, arquitetura e comandos.
- [x] Coleção Insomnia incluída.
- [x] Documentação de arquitetura e implantação incluída em `docs/`.
- [x] Repositório público e workflow de CI anterior executado com sucesso.
- [ ] Novo workflow CI/CD executado com build, testes e publicação da imagem GHCR — verificar em Actions após o commit.
- [ ] Staging implantado e verificado — depende de host, secrets, variável `ENABLE_STAGING_DEPLOY` e execução real.
- [ ] Produção implantada e verificada — depende de host, secrets, variável `ENABLE_PRODUCTION_DEPLOY` e aprovação.
- [ ] Capturas de tela reais anexadas — gerar após execução dos ambientes.

**Importante:** os itens não marcados dependem de infraestrutura, secrets e execução real. Não foram criadas capturas fictícias nem declarados deploys inexistentes.
