# Status de validação — Cidades ESG Inteligentes

**Atualizado em 15/10/2026**

## Evidências verificadas

- Repositório público: https://github.com/hevilynslopes-droid/cidades-esg-inteligentes
- Branch principal: `main`.
- Execução do workflow CI/CD: https://github.com/hevilynslopes-droid/cidades-esg-inteligentes/actions/runs/38787510204
- Run #16, commit `f7ec5099e2c412797d54f31d7627601e57d011ca`, concluído com resultado **success** em 15/10/2026.
- Job `Build e testes automatizados`: sucesso. O comando `mvn -B clean verify` terminou com sucesso e o relatório Surefire foi publicado como artefato.
- Job `Construir e publicar imagem no GHCR`: sucesso. A imagem foi construída e publicada com tag SHA e `latest`.
- Jobs `Deploy em staging` e `Deploy em produção`: **skipped**. O projeto ainda não tem servidor configurado; esses ambientes não foram implantados.
- Código-fonte, Dockerfile, Docker Compose, workflow, coleção Insomnia e documentação estão no repositório.

## Pendências para concluir a implantação

- [ ] Criar infraestrutura de hospedagem e validar disponibilidade/custo dos recursos.
- [ ] Configurar environments `staging` e `production` no GitHub.
- [ ] Cadastrar secrets SSH e arquivo `.env` seguro em cada host.
- [ ] Habilitar `ENABLE_STAGING_DEPLOY=true` somente após staging estar pronto; executar e verificar a API.
- [ ] Configurar aprovação obrigatória em `production`, habilitar `ENABLE_PRODUCTION_DEPLOY=true` apenas quando pronto, executar manualmente e validar.
- [ ] Registrar capturas reais de staging/produção depois dos deploys.

## Limitações e segurança

O CI e a publicação da imagem foram verificados; staging e produção não foram concluídos. Não foram inventadas credenciais nem capturas de tela. Não versionar `.env`, chaves privadas, tokens ou senhas. Se credenciais tiverem sido expostas anteriormente, rotacione-as.
