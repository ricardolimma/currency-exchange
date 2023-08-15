# Projeto Currency Exchange

Bem-vindo ao projeto Currency Exchange. Este repositório abriga uma aplicação projetada para recuperar dados de uma API externa, filtrar e processar informações e, em seguida, armazená-las em um banco de dados. Os dados coletados podem ser convenientemente consumidos pelo frontend da aplicação.

## Funcionalidades

### Buscar Todas as Moedas

Recuperar uma lista de todas as moedas disponíveis.

- **Método:** GET
- **URL:** `/api/moedas`
- **Respostas:**
  - **200 OK:** Retorna com sucesso a lista de moedas.
  - **400 Solicitação Inválida:** Parâmetros inválidos na solicitação.
  - **422 Entidade Não Processável:** Dados da solicitação malformados.
  - **500 Erro Interno do Servidor:** Erro encontrado ao buscar moedas.

### Recuperar Moeda por ID

Recuperar informações detalhadas sobre uma moeda específica usando o seu ID.

- **Método:** GET
- **URL:** `/api/moedas/{id}`
- **Parâmetros:**
  - **id (Path):** O ID da moeda a ser recuperada.
- **Respostas:**
  - **200 OK:** Retorna com sucesso a moeda correspondente ao ID fornecido.
  - **400 Solicitação Inválida:** Parâmetros inválidos na solicitação.
  - **422 Entidade Não Processável:** Dados da solicitação malformados.
  - **500 Erro Interno do Servidor:** Erro encontrado ao recuperar a moeda.

## Pré-requisitos

- Java 8 ou superior.
- Banco de dados configurado e acessível.
- Conexão ativa com a API externa de moedas.

## Configuração

1. Clone o repositório do projeto "Currency Exchange" para a sua máquina local.
2. Configure as propriedades do banco de dados no arquivo `application.properties`.
3. Configure as informações da API externa no arquivo `CurrencyExchangeRatesApiServiceImpl`.
4. Execute a aplicação.

## Uso

Após iniciar a aplicação, você pode utilizar os seguintes endpoints para acessar as funcionalidades:

- Para recuperar uma lista de todas as moedas:
  - Método: GET
  - URL: `http://localhost:8080/api/moedas`

- Para recuperar informações detalhadas sobre uma moeda específica usando o ID:
  - Método: GET
  - URL: `http://localhost:8080/api/moedas/{id}`
  - Substitua `{id}` pelo ID real da moeda desejada.

## Contribuição

O projeto "Currency Exchange" aceita contribuições. Sinta-se à vontade para enviar pull requests para melhorias ou correções.

## Autores

- [Ricardo Lima](https://github.com/ricardolimma)
- [Gabriel Henrique](https://github.com/GabrielHenriqueCA)

## Licença

Este projeto está licenciado sob a Licença MIT License. Consulte o arquivo LICENSE para obter mais detalhes.

---
