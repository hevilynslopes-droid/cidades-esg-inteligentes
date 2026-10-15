# Configuração do GitHub Actions e deploy

## 1. Workflow
O workflow `.github/workflows/ci-cd.yml` executa:
1. Build e testes com Java 21 e Maven Wrapper.
2. Publicação dos relatórios de testes como artefato.
3. Build da imagem Docker e publicação no GHCR com tags do commit e `latest`.
4. Deploy de staging após push na branch `main`, somente se `ENABLE_STAGING_DEPLOY=true`.
5. Deploy de produção por execução manual (`workflow_dispatch`), selecionando `deploy_production=true`, somente se `ENABLE_PRODUCTION_DEPLOY=true`.

## 2. Secrets e variáveis
Em **Settings → Secrets and variables → Actions**, configure as variáveis (não os secrets) somente após preparar os servidores:
- `ENABLE_STAGING_DEPLOY=true`
- `ENABLE_PRODUCTION_DEPLOY=true`

Secrets de staging (no environment `staging`):
- `STAGING_HOST`: DNS/IP do host.
- `STAGING_USER`: usuário SSH.
- `STAGING_SSH_KEY`: chave privada SSH dedicada.
- `STAGING_PATH`: diretório de deploy já criado no servidor.

Secrets de produção (no environment `production`):
- `PRODUCTION_HOST`
- `PRODUCTION_USER`
- `PRODUCTION_SSH_KEY`
- `PRODUCTION_PATH`
- `GHCR_READ_TOKEN`

Configure aprovação obrigatória para o environment `production`. Nunca coloque os valores dos secrets no código, nos arquivos versionados ou em mensagens de chat.

## 3. Preparar cada host
Instale Docker Engine, Docker Compose v2 e `curl`; permita ao usuário SSH executar Docker. Crie o diretório de deploy e um arquivo `.env` com valores fortes e exclusivos:
```dotenv
ORACLE_PASSWORD=senha_forte_unica
APP_USER=carbono_app
APP_USER_PASSWORD=outra_senha_forte_unica
JWT_SECRET=segredo_aleatorio_com_pelo_menos_32_bytes
```
Restringir permissões do arquivo (por exemplo, `chmod 600 .env`). Staging e produção devem usar hosts, credenciais e dados separados. Não exponha a porta do banco na internet.

## 4. Executar e validar
- Push na branch `main`: build/testes e publicação da imagem; staging só executa se habilitado por variável e secrets.
- Para produção: Actions → CI/CD - Cidades ESG Inteligentes → Run workflow → marcar `deploy_production`; produção só executa se habilitada e após aprovação do environment, quando configurada.
- O workflow consulta `http://127.0.0.1:8080/api/v1/actuator/health` no servidor por até cinco minutos.
- Após cada execução, registrar evidências reais da aba Actions, imagem GHCR e teste funcional. Health check confirma disponibilidade básica, não todos os comportamentos da API.

## 5. Estado real
O código do pipeline está preparado, mas os deploys não são considerados concluídos até que os hosts, secrets, variáveis e verificações pós-deploy tenham sido executados. Não use screenshots simuladas como evidência.
