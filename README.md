# Sistema de Agendamento de Transferências Financeiras

## Visão Geral

Este projeto é um sistema de agendamento de transferências financeiras desenvolvido como parte de um desafio prático. Ele foi construído utilizando **Java 11**, **Spring Boot**, e **H2 Database** para persistência dos dados. O sistema oferece funcionalidades como:

- Cadastro de usuários.
- Agendamento de transferências financeiras.
- Cálculo de taxas baseadas na data de agendamento.
- Visualização de extrato de transferências por conta.
- Autenticação com JWT para segurança das requisições.

## Decisões Arquiteturais

1. **Camadas do projeto:**

   - **Controllers:** Responsáveis por gerenciar as requisições HTTP e retornar as respostas adequadas.
   - **Services:** Centralizam as regras de negócio do sistema, como cálculo de taxas e validações de transferências.
   - **Repositories:** Lidam com a persistência dos dados, utilizando JPA para interação com o banco de dados.
   - **Models e DTOs:** Representam os dados do sistema e os objetos transferidos entre as camadas.

2. **Segurança:**

   - Foi implementada autenticação baseada em JWT para proteger os endpoints do sistema. Apenas os endpoints de login e criação de usuários são públicos.

3. **Banco de Dados:**

   - O sistema utiliza o H2 Database, um banco de dados em memória, facilitando os testes e o desenvolvimento local.

4. **Padrões de código:**

   - Foram aplicadas boas práticas de divisão de responsabilidades, organização em pacotes e tratamento de erros globais.

## Tecnologias Utilizadas

- **Java 11**: Linguagem principal do projeto.
- **Spring Boot**: Framework para desenvolvimento da API.
- **H2 Database**: Banco de dados em memória para persistência.
- **JWT (Json Web Token)**: Para autenticação e segurança das requisições.
- **Maven**: Gerenciador de dependências.

## Funcionalidades

1. **Autenticação e Autorização:**

   - Login com e-mail e senha.
   - Geração de tokens JWT para acesso aos endpoints protegidos.

2. **Cadastro de Usuários:**

   - Endpoint para criar usuários com e-mail, senha e geração automática de número de conta.

3. **Agendamento de Transferências:**

   - Permite agendar transferências entre contas, com validação de dados e cálculo de taxas baseado na tabela fornecida.

4. **Extrato de Transferências:**

   - Visualização de transferências realizadas e recebidas para uma conta específica.

5. **Listagem e Exclusão de Transferências:**

   - Listagem de todas as transferências cadastradas.
   - Exclusão de transferências pelo ID.

## Como Executar o Projeto

1. **Clonar o repositório:**

   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd <PASTA_DO_REPOSITORIO>
   ```

2. **Configurar o ambiente:**

   - Certifique-se de ter o **Java 11** instalado.
   - Configure sua IDE para importar o projeto como um projeto Maven.

3. **Executar o projeto:**

   - Execute a classe principal do Spring Boot: `ApiApplication`.
   - O projeto estará disponível em `http://localhost:8080`.

4. **Testar os endpoints:**

   - Utilize uma ferramenta como **Postman** ou **Insomnia** para testar os endpoints.

## Endpoints Principais

### **Autenticação:**

- **POST** `/api/auth/login`
  - **Corpo da requisição:**
    ```json
    {
        "usuarioDTO": {
            "email": "teste@teste.com",
            "senha": "123456"
        }
    }
    ```
  - **Resposta:**
    ```json
    {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "id": 1,
        "email": "teste@teste.com",
        "numeroConta": "123456789"
    }
    ```

### **Usuários:**

- **POST** `/api/usuarios`
  - **Corpo da requisição:**
    ```json
    {
        "nome": "João Silva",
        "email": "joao@teste.com",
        "senha": "123456"
    }
    ```
  - **Resposta:**
    ```json
    {
        "id": 1,
        "nome": "João Silva",
        "email": "joao@teste.com",
        "senha": "$2a$10$...",
        "numeroConta": "987654321",
        "transferenciasRealizadas": [],
        "transferenciasRecebidas": []
    }
    ```

### **Transferências:**

- **POST** `/api/transferencias`

  - **Corpo da requisição:**
    ```json
    {
        "valor": 1000.00,
        "dataTransferencia": "2025-02-01",
        "contaOrigem": "123456789",
        "contaDestino": "987654321"
    }
    ```
  - **Resposta:**
    ```json
    {
        "id": 1,
        "valor": 1000.00,
        "taxa": 12.00,
        "dataTransferencia": "2025-02-01",
        "dataAgendamento": "2025-01-26",
        "contaOrigem": "123456789",
        "contaDestino": "987654321"
    }
    ```

- **GET** `/api/transferencias`

  - **Resposta:**
    ```json
    [
        {
            "id": 1,
            "valor": 1000.00,
            "taxa": 12.00,
            "dataTransferencia": "2025-02-01",
            "dataAgendamento": "2025-01-26",
            "contaOrigem": "123456789",
            "contaDestino": "987654321"
        }
    ]
    ```

- **GET** `/api/transferencias/extrato/{numeroConta}`

  - **Resposta:**
    ```json
    [
        {
            "id": 1,
            "valor": 1000.00,
            "taxa": 12.00,
            "dataTransferencia": "2025-02-01",
            "dataAgendamento": "2025-01-26",
            "contaOrigem": "123456789",
            "contaDestino": "987654321"
        }
    ]
    ```

- **DELETE** `/api/transferencias/{id}`

  - **Resposta:**
    ```
    HTTP 204 No Content
    ```

## Considerações Finais

O projeto atende os requisitos principais do desafio, priorizando a organização e boas práticas de desenvolvimento.
