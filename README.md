# 📦 API Restful Fornecedores — Clean Code & SOLID

API RESTful desenvolvida em **Java 11** com **Spring Boot 2.6**, **Spring JDBC (JdbcTemplate)**, **Thymeleaf** e banco de dados **SQLite**, refatorada aplicando os princípios de **Clean Code** e **SOLID**, organizada em camadas inspiradas em **Arquitetura Limpa (Clean Architecture / Hexagonal)**.

O projeto disponibiliza:
- Uma **interface Web** (Thymeleaf + Bootstrap) para o CRUD de fornecedores;
- Uma **API REST** completa (CRUD + geração de relatório em PDF);
- Documentação interativa via **Swagger / OpenAPI**;
- Geração de **relatórios em PDF** com **JasperReports**.

---

## 🗂️ Sumário

- [Tecnologias utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do projeto](#-arquitetura-do-projeto)
- [Estrutura de pastas](#-estrutura-de-pastas)
- [Boas práticas e princípios aplicados](#-boas-práticas-e-princípios-aplicados)
  - [Clean Code](#clean-code)
  - [Princípios SOLID](#princípios-solid)
  - [Clean Architecture / Arquitetura em Camadas](#clean-architecture--arquitetura-em-camadas)
- [Modelo de dados](#-modelo-de-dados)
- [Como executar o projeto](#-como-executar-o-projeto)
- [Endpoints da API](#-endpoints-da-api)
- [Interface Web](#-interface-web)
- [Tratamento de exceções](#-tratamento-de-exceções)
- [Relatório em PDF (JasperReports)](#-relatório-em-pdf-jasperreports)
- [Documentação Swagger / OpenAPI](#-documentação-swagger--openapi)
- [Possíveis evoluções futuras](#-possíveis-evoluções-futuras)
- [Licença](#-licença)

---

## 🚀 Tecnologias utilizadas

| Tecnologia | Finalidade |
|---|---|
| **Java 11** | Linguagem de programação |
| **Spring Boot 2.6.1** | Framework principal da aplicação |
| **Spring Web (MVC)** | Construção da API REST e dos endpoints web |
| **Spring JDBC (JdbcTemplate)** | Persistência de dados sem uso de ORM |
| **Thymeleaf** | Motor de templates para a interface web |
| **SQLite (sqlite-jdbc)** | Banco de dados embarcado (`fornecedores.DB`) |
| **JasperReports 6.21.3** | Geração de relatórios em PDF |
| **springdoc-openapi-ui** | Documentação interativa via Swagger UI |
| **Bootstrap 5** | Estilização das páginas web |
| **Maven** | Gerenciador de dependências e build |
| **JUnit 5 / Spring Boot Test** | Testes automatizados |

---

## 🏗️ Arquitetura do projeto

O projeto foi organizado seguindo uma abordagem inspirada em **Clean Architecture** (Arquitetura Limpa), separando claramente as responsabilidades em camadas independentes, o que facilita testes, manutenção e a substituição de tecnologias sem impactar as regras de negócio.

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                     │
│   (Controllers REST e Web — entrada/saída do usuário)     │
└───────────────────────────┬───────────────────────────────┘
                              │
┌───────────────────────────▼───────────────────────────────┐
│                    APPLICATION LAYER                        │
│   (Use Cases e Services — regras de aplicação/orquestração) │
└───────────────────────────┬───────────────────────────────┘
                              │
┌───────────────────────────▼───────────────────────────────┐
│                       DOMAIN LAYER                          │
│   (Entidades e Ports/Interfaces — regras de negócio puras)  │
└───────────────────────────┬───────────────────────────────┘
                              │
┌───────────────────────────▼───────────────────────────────┐
│                   INFRASTRUCTURE LAYER                       │
│  (JDBC, RowMapper, JasperReports — detalhes de tecnologia)  │
└─────────────────────────────────────────────────────────────┘
```

A regra de dependência segue de fora para dentro: as camadas externas (presentation, infrastructure) dependem das camadas internas (application, domain), e nunca o contrário. A comunicação entre `application` e `infrastructure` ocorre por meio de **interfaces (Ports)**, definidas no domínio e implementadas na infraestrutura.

---

## 📁 Estrutura de pastas

```
src/main/java/com/javaricci/cleancodesolid/
│
├── CleanCodeSolidApplication.java         # Classe principal (Spring Boot)
│
├── Config/
│   ├── OpenApiConfigCleanCodeSolid.java   # Configuração do Swagger/OpenAPI
│   └── WebConfigCleanCodeSolid.java       # Configuração de CORS
│
├── shared/
│   └── exception/
│       ├── GlobalExceptionHandlerCleanCodeSolid.java   # Tratamento global de exceções
│       └── RelatorioExceptionCleanCodeSolid.java       # Exceção customizada de relatório
│
└── fornecedor/
    │
    ├── domain/                             # Camada de domínio (regras de negócio)
    │   ├── model/
    │   │   └── FornecedorCleanCodeSolid.java          # Entidade de domínio
    │   └── port/
    │       └── FornecedorRepositoryPortCleanCodeSolid.java  # Interface (porta) do repositório
    │
    ├── application/                        # Camada de aplicação (casos de uso)
    │   ├── usecase/
    │   │   ├── ListarFornecedorUseCaseCleanCodeSolid.java
    │   │   ├── BuscarFornecedorUseCaseCleanCodeSolid.java
    │   │   ├── SalvarFornecedorUseCaseCleanCodeSolid.java
    │   │   └── ExcluirFornecedorUseCaseCleanCodeSolid.java
    │   └── service/
    │       └── GerarRelatorioFornecedorServiceCleanCodeSolid.java
    │
    ├── infrastructure/                     # Camada de infraestrutura (detalhes técnicos)
    │   ├── jdbc/
    │   │   ├── FornecedorRepositoryJdbcCleanCodeSolid.java  # Implementação do Port via JdbcTemplate
    │   │   └── FornecedorRowMapperCleanCodeSolid.java       # Mapeamento ResultSet -> Entidade
    │   └── report/
    │       └── JasperReportProviderCleanCodeSolid.java      # Compilação do relatório .jrxml
    │
    └── presentation/                       # Camada de apresentação (entrada/saída)
        ├── api/
        │   └── ApiFornecedorControllerCleanCodeSolid.java   # Controller REST (JSON)
        └── web/
            └── WebFornecedorControllerCleanCodeSolid.java   # Controller Web (Thymeleaf)

src/main/resources/
├── application.properties
├── schema.sql                              # Script de criação da tabela FORNECEDORES
├── reports/
│   └── FornecedoresCleanCodeSolid.jrxml    # Template do relatório JasperReports
└── templates/fornecedores/
    ├── listar.html
    └── formulario.html
```

---

## ✅ Boas práticas e princípios aplicados

### Clean Code

As seguintes práticas de Código Limpo foram aplicadas durante a refatoração:

- **Nomes significativos e consistentes**: classes, métodos e variáveis com nomes que expressam claramente sua intenção (`ListarFornecedorUseCase`, `excluirUseCase`, `validar`, `obterRelatorioCompilado`).
- **Métodos pequenos e com responsabilidade única**: cada método executa uma única ação, como `inserir()`, `atualizar()` e `excluir()` no repositório, ou `executar()` em cada *use case*.
- **Separação de leitura e escrita de SQL**: instruções `SQL` isoladas dentro do repositório, sem misturar lógica de negócio com persistência.
- **Eliminação de duplicação (DRY)**: o `RowMapper` centraliza a conversão de `ResultSet` para entidade, evitando repetição em múltiplas consultas.
- **Tratamento centralizado de erros**: uso de `@ControllerAdvice` (`GlobalExceptionHandlerCleanCodeSolid`) para capturar exceções de validação e de geração de relatório, evitando blocos `try/catch` espalhados pelos controllers.
- **Imutabilidade de dependências**: todas as dependências injetadas são `final` e atribuídas via construtor, deixando explícito o que cada classe precisa para funcionar.
- **Validações de negócio isoladas**: a validação de campos obrigatórios (ex.: Razão Social) está encapsulada no *use case* de salvar, não no controller nem no repositório.
- **Separação entre modelo de domínio e camada de persistência**: a entidade `FornecedorCleanCodeSolid` não conhece detalhes de SQL, JDBC ou JSON.

### Princípios SOLID

| Princípio | Aplicação no projeto |
|---|---|
| **S — Single Responsibility Principle** | Cada classe possui uma única razão para mudar. Exemplos: `ListarFornecedorUseCaseCleanCodeSolid` apenas lista fornecedores; `FornecedorRowMapperCleanCodeSolid` apenas converte `ResultSet` em entidade; `JasperReportProviderCleanCodeSolid` apenas compila o relatório `.jrxml`; `GerarRelatorioFornecedorServiceCleanCodeSolid` apenas orquestra a geração do PDF. |
| **O — Open/Closed Principle** | A camada de persistência pode ser estendida (ex.: trocar SQLite por outro banco, ou criar uma nova implementação de repositório) sem alterar os *use cases*, pois eles dependem apenas da interface `FornecedorRepositoryPortCleanCodeSolid`. |
| **L — Liskov Substitution Principle** | `FornecedorRepositoryJdbcCleanCodeSolid` implementa `FornecedorRepositoryPortCleanCodeSolid` e pode ser substituída por qualquer outra implementação (ex.: `FornecedorRepositoryMemoriaCleanCodeSolid` para testes) sem quebrar o comportamento esperado pelos *use cases*. |
| **I — Interface Segregation Principle** | A interface `FornecedorRepositoryPortCleanCodeSolid` expõe apenas as operações realmente necessárias para o domínio (`buscarTodos`, `buscarPorId`, `salvar`, `excluir`), sem métodos genéricos ou não utilizados. |
| **D — Dependency Inversion Principle** | Os *use cases* (camada de aplicação) dependem da abstração `FornecedorRepositoryPortCleanCodeSolid` (definida no domínio), e não da implementação concreta `FornecedorRepositoryJdbcCleanCodeSolid` (definida na infraestrutura). A inversão de dependência é resolvida automaticamente pelo container de Injeção de Dependências do Spring. |

### Clean Architecture / Arquitetura em Camadas

- **Domain**: contém a entidade `FornecedorCleanCodeSolid` e a porta (`Port`) `FornecedorRepositoryPortCleanCodeSolid`, totalmente livres de anotações de framework relacionadas à persistência ou web.
- **Application**: contém os *use cases* (`Listar`, `Buscar`, `Salvar`, `Excluir`) e o serviço de geração de relatório, responsáveis por orquestrar as regras de aplicação.
- **Infrastructure**: contém os detalhes técnicos — implementação JDBC do repositório, `RowMapper` e o provedor de relatórios JasperReports.
- **Presentation**: contém os controllers REST (`ApiFornecedorControllerCleanCodeSolid`) e Web (`WebFornecedorControllerCleanCodeSolid`), responsáveis apenas por receber requisições, delegar para os *use cases* e devolver a resposta apropriada.
- **Shared**: contém componentes transversais, como exceções customizadas e o tratador global de exceções.

---

## 🗃️ Modelo de dados

Tabela `FORNECEDORES` (SQLite — `fornecedores.DB`), criada automaticamente na inicialização via `schema.sql`:

```sql
CREATE TABLE IF NOT EXISTS FORNECEDORES (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    RAZAOSOCIALFORNECEDOR VARCHAR(255),
    CNPJFORNECEDOR VARCHAR(30),
    ENDERECO VARCHAR(255),
    BAIRRO VARCHAR(100),
    MUNICIPIO VARCHAR(100),
    CEP VARCHAR(20)
);
```

| Campo | Tipo | Descrição |
|---|---|---|
| `ID` | INTEGER (PK, autoincrement) | Identificador único do fornecedor |
| `RAZAOSOCIALFORNECEDOR` | VARCHAR(255) | Razão social do fornecedor (obrigatório) |
| `CNPJFORNECEDOR` | VARCHAR(30) | CNPJ do fornecedor |
| `ENDERECO` | VARCHAR(255) | Endereço |
| `BAIRRO` | VARCHAR(100) | Bairro |
| `MUNICIPIO` | VARCHAR(100) | Município |
| `CEP` | VARCHAR(20) | CEP |

---

## ▶️ Como executar o projeto

### Pré-requisitos
- **JDK 11** instalado
- **Maven** (ou utilizar o Maven Wrapper incluído: `mvnw` / `mvnw.cmd`)

### Passo a passo

```bash
# 1. Clonar o repositório
git clone https://github.com/ESRicci26/API-Restful-CleanCodeSolid.git
cd API-Restful-CleanCodeSolid

# 2. Executar a aplicação com o Maven Wrapper
./mvnw spring-boot:run        # Linux/Mac
mvnw.cmd spring-boot:run       # Windows
```

A aplicação iniciará em `http://localhost:8080`. O banco de dados SQLite (`fornecedores.DB`) já está incluído no projeto e a tabela `FORNECEDORES` é criada/validada automaticamente na inicialização (`spring.sql.init.mode=always`).

---

## 🌐 Endpoints da API

Base path: `/api/fornecedores`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/fornecedores` | Lista todos os fornecedores |
| `GET` | `/api/fornecedores/{id}` | Busca um fornecedor pelo ID |
| `POST` | `/api/fornecedores` | Cadastra um novo fornecedor |
| `PUT` | `/api/fornecedores/{id}` | Atualiza um fornecedor existente |
| `DELETE` | `/api/fornecedores/{id}` | Remove um fornecedor |
| `GET` | `/api/fornecedores/relatorio` | Gera e retorna um relatório em PDF com todos os fornecedores |

### Exemplo de payload (POST/PUT)

```json
{
  "razaoSocial": "Empresa Exemplo LTDA",
  "cnpj": "12.345.678/0001-90",
  "endereco": "Rua das Flores, 123",
  "bairro": "Centro",
  "municipio": "São Paulo",
  "cep": "01000-000"
}
```

---

## 🖥️ Interface Web

Base path: `/fornecedores`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/fornecedores` | Lista os fornecedores cadastrados (Thymeleaf) |
| `GET` | `/fornecedores/novo` | Exibe formulário para novo fornecedor |
| `POST` | `/fornecedores/salvar` | Salva (cria ou atualiza) um fornecedor |
| `GET` | `/fornecedores/editar/{id}` | Exibe formulário preenchido para edição |
| `GET` | `/fornecedores/deletar/{id}` | Remove um fornecedor e redireciona para a listagem |

A interface utiliza **Bootstrap 5** para estilização e oferece um botão para download do **relatório em PDF** diretamente da listagem.

---

## ⚠️ Tratamento de exceções

O tratamento de erros é centralizado em `GlobalExceptionHandlerCleanCodeSolid`, utilizando `@ControllerAdvice`:

| Exceção | Resposta HTTP | Cenário |
|---|---|---|
| `IllegalArgumentException` | `400 Bad Request` | Falha de validação de regra de negócio (ex.: Razão Social não informada) |
| `RelatorioExceptionCleanCodeSolid` | `500 Internal Server Error` | Falha ao compilar/gerar o relatório PDF |

Essa abordagem evita a repetição de blocos `try/catch` nos controllers e padroniza as respostas de erro da API.

---

## 📄 Relatório em PDF (JasperReports)

O endpoint `GET /api/fornecedores/relatorio` gera um relatório em PDF com a lista completa de fornecedores, utilizando **JasperReports**:

1. `JasperReportProviderCleanCodeSolid` carrega e compila o template `FornecedoresCleanCodeSolid.jrxml`;
2. `GerarRelatorioFornecedorServiceCleanCodeSolid` busca os dados via `ListarFornecedorUseCaseCleanCodeSolid`;
3. Os dados são convertidos em `JRBeanCollectionDataSource` e combinados ao template via `JasperFillManager`;
4. O relatório é exportado como PDF (`JasperExportManager.exportReportToPdfStream`) e retornado como `byte[]` na resposta HTTP.

---

## 📘 Documentação Swagger / OpenAPI

A documentação interativa da API é gerada automaticamente com **springdoc-openapi-ui** e configurada em `OpenApiConfigCleanCodeSolid`.

Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔮 Possíveis evoluções futuras

- Adicionar camada de **DTOs** para desacoplar completamente a entidade de domínio dos contratos da API.
- Implementar **testes unitários** para *use cases* e **testes de integração** para os repositórios (ex.: com banco H2/SQLite em memória).
- Adicionar **paginação e filtros** na listagem de fornecedores.
- Adicionar **validação de CNPJ** com Bean Validation (`javax.validation`).
- Internacionalização (i18n) das mensagens de erro e telas Thymeleaf.
- Migrar configuração de URLs hardcoded de CORS (`WebConfigCleanCodeSolid`) para variáveis de ambiente/perfis (`dev`, `prod`).

---

## 📝 Licença

Este projeto está disponível para fins de estudo e portfólio. Sinta-se à vontade para utilizá-lo como referência de boas práticas em **Clean Code**, **SOLID** e **Clean Architecture** com Spring Boot.
