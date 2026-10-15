# Status de validação — Cidades ESG Inteligentes

Atualizado em 15/10/2026.

## Evidências verificadas
- Repositório GitHub público: https://github.com/hevilynslopes-droid/cidades-esg-inteligentes
- Branch principal: `main`.
- O repositório contém código-fonte, Dockerfile, Compose, coleção Insomnia, relatório PDF e apresentação PPTX.
- Uma execução anterior do workflow de CI simples foi concluída com sucesso em 14/10/2026. Essa execução confirma o build/testes daquele workflow, não confirma publicação de imagem nem deploy.
- O workflow completo `.github/workflows/ci-cd.yml` foi adicionado para build/testes, publicação no GHCR e deploys condicionais.
- A primeira execução do workflow novo identificou que o arquivo `mvnw` não tinha permissão de execução no Git; o workflow foi ajustado para executar `chmod +x ./mvnw` antes dos testes. A execução seguinte deve confirmar se essa correção é suficiente.

## Pendências externas
- [ ] Confirmar que a execução do novo workflow CI/CD terminou com sucesso e que a imagem foi publicada no GHCR.
- [ ] Configurar servidor de staging, secrets e variável `ENABLE_STAGING_DEPLOY=true`; executar e verificar o health check.
- [ ] Configurar servidor de produção, secrets e variável `ENABLE_PRODUCTION_DEPLOY=true`; exigir aprovação do environment `production` e executar manualmente.
- [ ] Guardar capturas de tela reais da execução, da imagem e dos ambientes implantados.

## Limitações e segurança
Os deploys de staging e produção não podem ser declarados concluídos sem acesso aos hosts e configuração de secrets. Não foram inventadas capturas de tela nem resultados de execução. Não versionar `.env`, chaves privadas, tokens ou senhas. Se credenciais de um arquivo `.env` anterior foram compartilhadas ou publicadas, rotacione-as.
