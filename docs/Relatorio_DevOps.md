# Relatório técnico — Cidades ESG Inteligentes

**Disciplina:** DevOps  
**Projeto-base:** Carbono API — gestão de emissões e sustentabilidade  
**Integrantes:** preencher com os nomes da equipe  
**Data:** preencher na submissão

## 1. Objetivo

Preparar a API de gestão de carbono para um fluxo DevOps reproduzível, reduzindo erros manuais entre alteração de código, testes, empacotamento e implantação. A solução mantém a implementação Java/Spring Boot recebida e acrescenta automação, containerização e orientações operacionais.

## 2. Projeto e tecnologias

A API está organizada em controllers, services, repositories, entities, DTOs e configuração de segurança. O banco Oracle utiliza migrações Flyway. A autenticação é baseada em Spring Security e JWT. A coleção Insomnia recebida foi incluída para validação manual dos endpoints.

Tecnologias: Java 21, Spring Boot, Maven, Oracle Database Free, Flyway, Spring Security, JWT, JUnit 5, H2 para o perfil de teste, Docker, Docker Compose, GitHub Actions e GitHub Container Registry.

## 3. Pipeline CI/CD

O workflow `.github/workflows/ci-cd.yml` implementa as seguintes fases:

1. **Checkout:** recupera o commit do repositório.
2. **Build e testes:** configura Java 21, cache Maven e executa `mvn -B clean verify`.
3. **Relatórios:** publica o resultado Surefire como artefato do job.
4. **Build de imagem:** constrói imagem Docker somente após sucesso dos testes.
5. **Publicação:** envia tags com SHA do commit e `latest` para GHCR.
6. **Staging:** push na branch `main` inicia o deploy remoto quando os secrets e o servidor estiverem configurados.
7. **Produção:** deploy manual via `workflow_dispatch`, protegido pelo ambiente GitHub `production`.

A promoção é rastreável pelo SHA do commit, o que ajuda a identificar a versão implantada. Recomenda-se habilitar aprovação obrigatória para o ambiente de produção.

## 4. Containerização

O Dockerfile usa build multi-stage: Maven/Temurin 21 executa `clean verify`, enquanto a imagem final utiliza apenas JRE e o JAR produzido. O processo da aplicação é executado com usuário não-root. O Docker Compose local separa a API e o banco em uma rede bridge; o volume `oracle_data` mantém os dados do Oracle entre reinicializações. Credenciais e segredo JWT são fornecidos por variáveis de ambiente.

O Compose de deploy usa imagem versionada no GHCR e mantém a porta do Oracle fora da exposição pública. Para uso real na internet, recomenda-se proxy reverso com TLS, firewall, backups e monitoramento.

## 5. Testes e qualidade

A etapa automatizada executa Maven Verify e armazena os relatórios Surefire. O teste existente verifica o carregamento do contexto Spring e foi isolado com perfil `test`, H2 em memória e Flyway desabilitado. Esse teste é uma validação inicial, não substitui testes unitários por serviço/controller nem testes de integração contra Oracle. A coleção Insomnia permite executar verificações manuais dos endpoints.

**Resultado verificado em 15/10/2026:** o workflow GitHub Actions [run #16](https://github.com/hevilynslopes-droid/cidades-esg-inteligentes/actions/runs/38787510204) terminou com sucesso. O job `Build e testes automatizados` executou `mvn -B clean verify` com sucesso e publicou o relatório Surefire. O job de construção/publicação da imagem Docker no GHCR também terminou com sucesso. Os jobs de deploy para staging e produção foram ignorados (skipped), pois a hospedagem e os secrets ainda não estão configurados. A execução bem-sucedida de CI não é evidência de deploy.

## 6. Segurança

Foram removidas credenciais fixas do Compose e do arquivo principal de configuração. O `.env` real não é incluído no pacote; o `.env.example` contém apenas marcadores para substituição. O `.gitignore` impede versionamento acidental do `.env`. Segredos previamente expostos devem ser rotacionados. Em GitHub, credenciais de SSH e caminhos de deploy devem ser configurados como secrets por ambiente, nunca como texto no workflow.

## 7. Evidências da entrega

O pacote inclui os arquivos de automação e as instruções para produzir evidências reais. Após publicar o repositório e configurar a infraestrutura, anexar:

- captura do workflow com build e testes aprovados;
- captura da imagem publicada no GHCR com SHA;
- saída de `docker compose ps` no ambiente staging;
- teste funcional da API em staging;
- aprovação e resultado do deploy em produção;
- requisição de Insomnia bem-sucedida, com dados sensíveis ocultos.

Não foram inseridas imagens fictícias. No momento da geração deste documento, os deploys externos não foram executados por dependerem de repositório GitHub, servidores e secrets fornecidos pela equipe.

## 8. Desafios e soluções propostas

| Desafio | Tratamento |
|---|---|
| Segredos fixos no código/configuração | Variáveis de ambiente, `.env.example`, `.gitignore` e rotação dos valores expostos |
| Build que ignorava testes | Dockerfile executa `mvn clean verify` |
| Teste de contexto dependente do Oracle | Perfil `test` com H2 e Flyway desabilitado |
| Ausência de automação | Workflow GitHub Actions com dependência entre testes e publicação da imagem |
| Diferença entre ambientes | Compose de deploy com imagem versionada e secrets separados por ambiente |
| Evidências de execução | Artefatos de teste e roteiro para capturas reais após deploy |

## 9. Checklist obrigatório

- [x] ZIP com código-fonte da aplicação.
- [x] Dockerfile.
- [x] Docker Compose com volumes, variáveis e rede.
- [x] Pipeline com build, teste e etapas de deploy.
- [x] README com instruções de execução e CI/CD.
- [x] Documentação do projeto e arquitetura.
- [x] `.env.example` sem credenciais reais.
- [x] Coleção Insomnia incluída.
- [x] Pipeline CI/CD executado com sucesso no GitHub: build/testes e publicação da imagem GHCR — [run #16](https://github.com/hevilynslopes-droid/cidades-esg-inteligentes/actions/runs/38787510204).
- [ ] Staging implantado e testado — exige host e secrets.
- [ ] Produção implantada e testada — exige host, secrets e aprovação.
- [ ] Capturas reais anexadas — gerar depois das execuções.

## 10. Conclusão

A aplicação recebida foi preparada para adoção de práticas DevOps, com build multi-stage, execução de testes como gate, publicação de imagem e desenho de implantação por ambientes. A entrega de código e documentação está pronta para revisão; a conclusão operacional depende de executar a pipeline e comprovar staging e produção com infraestrutura real.
