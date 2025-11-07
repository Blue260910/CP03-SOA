# 🛠️ Sistema de Suporte Técnico - API RESTful

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> **API RESTful para gerenciamento de solicitações de suporte técnico**  
> Desenvolvida como parte do Checkpoint 3 - Arquitetura Orientada a Serviço - FIAP

---

## 📑 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Arquitetura](#-arquitetura)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Execução](#-instalação-e-execução)
- [Endpoints da API](#-endpoints-da-api)
- [Regras de Negócio](#-regras-de-negócio)
- [Modelos de Dados](#-modelos-de-dados)
- [Exemplos de Uso](#-exemplos-de-uso)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Status Codes Utilizados](#-status-codes-utilizados)
- [Perguntas Discursivas](#-perguntas-discursivas)
- [Contribuidores](#-contribuidores)

---

## 🎯 Sobre o Projeto

Este projeto implementa uma **API RESTful** completa para gerenciamento de solicitações de suporte técnico em um ambiente corporativo. A aplicação permite que usuários criem chamados, acompanhem seus status e que a equipe de suporte gerencie todo o ciclo de vida dos atendimentos.

### Objetivos

- ✅ Implementar uma API RESTful clara e organizada
- ✅ Aplicar operações CRUD completas
- ✅ Implementar regras de negócio robustas
- ✅ Utilizar Status Codes HTTP apropriados
- ✅ Tratar erros e exceções de forma controlada
- ✅ Seguir arquitetura em camadas (Separation of Concerns)
- ✅ Armazenar dados em memória (sem dependência de banco de dados)

---

## 🏗️ Arquitetura

O projeto segue uma **arquitetura em camadas**, garantindo separação de responsabilidades e facilitando manutenção:

```
┌─────────────────────────────────────────────────┐
│          API Layer (Controllers)                │
│  - Recebe requisições HTTP                      │
│  - Valida entrada                               │
│  - Retorna respostas HTTP                       │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│       Service Layer (Business Logic)            │
│  - Regras de negócio                            │
│  - Validações complexas                         │
│  - Orquestração de operações                    │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│      Repository Layer (Data Access)             │
│  - Persistência em memória                      │
│  - CRUD operations                              │
│  - Thread-safe (ConcurrentHashMap)              │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│         Domain Layer (Models)                   │
│  - Entidades de domínio                         │
│  - Value Objects                                │
│  - Enums                                        │
└─────────────────────────────────────────────────┘
```

### Estrutura de Pacotes

```
br.com.fiap.byteshoponlineapp
├── api/                          # Camada de Apresentação
│   ├── SolicitacaoSuporteController.java
│   ├── dto/                      # Data Transfer Objects
│   │   ├── SolicitacaoSuporteRequest.java
│   │   ├── SolicitacaoSuporteResponse.java
│   │   ├── AtualizarSolicitacaoRequest.java
│   │   └── AtualizarStatusRequest.java
│   └── exception/                # Tratamento de Erros
│       ├── GlobalExceptionHandler.java
│       └── ErroResposta.java
├── service/                      # Camada de Negócio
│   ├── SolicitacaoSuporteService.java
│   └── exception/
│       ├── SolicitacaoNaoEncontradaException.java
│       ├── TransicaoStatusInvalidaException.java
│       └── RegraDeNegocioException.java
├── domain/                       # Camada de Domínio
│   ├── SolicitacaoSuporte.java
│   ├── enums/
│   │   ├── StatusSolicitacao.java
│   │   └── Prioridade.java
│   └── repository/
│       └── SolicitacaoSuporteRepository.java
└── config/                       # Configurações
    └── OpenApiConfig.java
```

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Java** | 17 | Linguagem de programação |
| **Spring Boot** | 3.2.0 | Framework para aplicações Java |
| **Spring Web** | 3.2.0 | Módulo para APIs REST |
| **Spring Validation** | 3.2.0 | Validação de dados |
| **SpringDoc OpenAPI** | 2.5.0 | Documentação Swagger/OpenAPI |
| **Maven** | 3.9+ | Gerenciador de dependências |

### Principais Features do Spring Utilizadas

- **Spring MVC** - Para criação de endpoints REST
- **Bean Validation** - Para validação de entrada (@Valid, @NotBlank, etc.)
- **Dependency Injection** - Para inversão de controle
- **Exception Handling** - Com @ControllerAdvice

---

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- ☑️ **Java JDK 17** ou superior
  - [Download Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
  - Verificar instalação: `java -version`

- ☑️ **Maven 3.9+** (opcional - o projeto inclui Maven Wrapper)
  - Verificar instalação: `mvn -version`

- ☑️ **IDE** (recomendado)
  - IntelliJ IDEA, Eclipse, ou VS Code com extensões Java

---

## ⚙️ Instalação e Execução

### 1️⃣ Clone o Repositório

```bash
git clone <url-do-repositorio>
cd ByteShopOnlineApp
```

### 2️⃣ Compile o Projeto

**Windows:**
```bash
.\mvnw.cmd clean package
```

**Linux/Mac:**
```bash
./mvnw clean package
```

### 3️⃣ Execute a Aplicação

**Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

**Ou execute o JAR gerado:**
```bash
java -jar target/sistema-suporte-tecnico-1.0.0.jar
```

### 4️⃣ Acesse a Aplicação

- **API Base URL**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

---

## 🔌 Endpoints da API

### Base URL
```
http://localhost:8080/api/suporte
```

### Resumo dos Endpoints

| Método | Endpoint | Descrição | Status Code Sucesso |
|--------|----------|-----------|---------------------|
| POST | `/api/suporte` | Criar nova solicitação | 201 Created |
| GET | `/api/suporte` | Listar todas as solicitações | 200 OK |
| GET | `/api/suporte/{id}` | Buscar solicitação por ID | 200 OK |
| GET | `/api/suporte/status/{status}` | Listar por status | 200 OK |
| PUT | `/api/suporte/{id}` | Atualizar solicitação | 200 OK |
| PATCH | `/api/suporte/{id}/status` | Atualizar status | 200 OK |
| PATCH | `/api/suporte/{id}/encerrar` | Encerrar solicitação | 200 OK |
| DELETE | `/api/suporte/{id}` | Remover solicitação | 204 No Content |
| GET | `/api/suporte/estatisticas` | Obter estatísticas | 200 OK |

---

## 📊 Regras de Negócio

### 1. **Validação de Campos**

#### Campos Obrigatórios na Criação
- **Título**: 5 a 100 caracteres
- **Descrição**: 10 a 500 caracteres
- **Prioridade**: BAIXA, MEDIA, ALTA ou CRITICA

#### Validações Automáticas
- Status inicial sempre **ABERTA**
- Data de criação definida automaticamente
- Data de atualização atualizada em cada modificação

---

### 2. **Transições de Status (Máquina de Estados)**

Esta é a **principal regra de negócio** do sistema. As transições de status seguem um fluxo controlado:

```
┌─────────────────────────────────────────────────────────┐
│                  CICLO DE VIDA                          │
└─────────────────────────────────────────────────────────┘

    ABERTA ──────────────────┐
      │                      │
      │ (iniciar)            │ (cancelar)
      ↓                      ↓
  EM_ANDAMENTO          FECHADA
      │                      ↑
      │ (resolver)           │
      ↓                      │
   RESOLVIDA ────────────────┘
      │                (encerrar)
      │ (reabrir)
      ↓
  EM_ANDAMENTO
```

#### Regras Detalhadas

| Status Atual | Transições Permitidas | Transições Bloqueadas |
|--------------|----------------------|----------------------|
| **ABERTA** | → EM_ANDAMENTO<br>→ FECHADA | → RESOLVIDA<br>→ ABERTA |
| **EM_ANDAMENTO** | → RESOLVIDA<br>→ ABERTA | → FECHADA<br>→ EM_ANDAMENTO |
| **RESOLVIDA** | → FECHADA<br>→ EM_ANDAMENTO | → ABERTA<br>→ RESOLVIDA |
| **FECHADA** | ❌ **NENHUMA** | ✅ **TODAS** |

**Exemplo Prático:**
```
✅ PERMITIDO: ABERTA → EM_ANDAMENTO → RESOLVIDA → FECHADA
❌ BLOQUEADO: ABERTA → RESOLVIDA (pula etapa!)
❌ BLOQUEADO: FECHADA → qualquer status (imutável!)
```

---

### 3. **Regra de Atualização**

**Não é possível atualizar solicitações FECHADAS**

- Solicitações com status `FECHADA` são **somente leitura**
- Tentativa de atualização retorna erro 400 Bad Request
- Campos que podem ser atualizados (quando não fechada):
  - Título
  - Descrição
  - Prioridade

---

### 4. **Regra de Encerramento**

**Só pode encerrar solicitações RESOLVIDAS**

```java
Para encerrar (PATCH /api/suporte/{id}/encerrar):
  → Status atual DEVE ser RESOLVIDA
  → Se não for, retorna erro 400
```

**Fluxo Correto:**
```
EM_ANDAMENTO → RESOLVIDA → encerrar() → FECHADA ✅
EM_ANDAMENTO → encerrar() → ❌ ERRO!
```

---

### 5. **Regra de Remoção**

**Só pode remover solicitações ABERTAS**

```java
Para remover (DELETE /api/suporte/{id}):
  → Status DEVE ser ABERTA
  → Outros status: retorna erro 400
```

**Justificativa:** Evita perda de histórico de solicitações já em atendimento.

---

## 📦 Modelos de Dados

### SolicitacaoSuporte (Entidade Principal)

```json
{
  "id": 1,
  "titulo": "Erro ao fazer login no sistema",
  "descricao": "Ao tentar fazer login, recebo mensagem de erro 'Credenciais inválidas' mesmo com senha correta",
  "status": "ABERTA",
  "prioridade": "ALTA",
  "dataCriacao": "2025-11-06T21:00:00",
  "dataAtualizacao": "2025-11-06T21:00:00"
}
```

### Enums

#### StatusSolicitacao
```java
ABERTA          // Solicitação criada, aguardando atendimento
EM_ANDAMENTO    // Equipe está trabalhando na solução
RESOLVIDA       // Problema resolvido, aguardando confirmação
FECHADA         // Solicitação finalizada (imutável)
```

#### Prioridade
```java
BAIXA    // Pode aguardar, sem urgência
MEDIA    // Importante, mas não urgente
ALTA     // Urgente, afeta produtividade
CRITICA  // Muito urgente, sistema parado
```

---

## 💡 Exemplos de Uso

### 1. Criar Nova Solicitação

**Request:**
```bash
POST http://localhost:8080/api/suporte
Content-Type: application/json

{
  "titulo": "Sistema lento após atualização",
  "descricao": "Após a última atualização, o sistema está demorando mais de 30 segundos para carregar a página inicial",
  "prioridade": "ALTA"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "titulo": "Sistema lento após atualização",
  "descricao": "Após a última atualização, o sistema está demorando mais de 30 segundos para carregar a página inicial",
  "status": "ABERTA",
  "prioridade": "ALTA",
  "dataCriacao": "2025-11-06T21:15:30",
  "dataAtualizacao": "2025-11-06T21:15:30"
}
```

---

### 2. Listar Todas as Solicitações

**Request:**
```bash
GET http://localhost:8080/api/suporte
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "titulo": "Sistema lento após atualização",
    "status": "ABERTA",
    "prioridade": "ALTA",
    "dataCriacao": "2025-11-06T21:15:30",
    "dataAtualizacao": "2025-11-06T21:15:30"
  },
  {
    "id": 2,
    "titulo": "Erro ao enviar email",
    "status": "EM_ANDAMENTO",
    "prioridade": "MEDIA",
    "dataCriacao": "2025-11-06T20:00:00",
    "dataAtualizacao": "2025-11-06T20:30:00"
  }
]
```

---

### 3. Buscar por ID

**Request:**
```bash
GET http://localhost:8080/api/suporte/1
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "titulo": "Sistema lento após atualização",
  "descricao": "Após a última atualização, o sistema está demorando mais de 30 segundos para carregar a página inicial",
  "status": "ABERTA",
  "prioridade": "ALTA",
  "dataCriacao": "2025-11-06T21:15:30",
  "dataAtualizacao": "2025-11-06T21:15:30"
}
```

---

### 4. Filtrar por Status

**Request:**
```bash
GET http://localhost:8080/api/suporte/status/ABERTA
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "titulo": "Sistema lento após atualização",
    "status": "ABERTA",
    "prioridade": "ALTA"
  },
  {
    "id": 3,
    "titulo": "Não consigo resetar senha",
    "status": "ABERTA",
    "prioridade": "MEDIA"
  }
]
```

---

### 5. Atualizar Solicitação

**Request:**
```bash
PUT http://localhost:8080/api/suporte/1
Content-Type: application/json

{
  "titulo": "Sistema muito lento após atualização - URGENTE",
  "descricao": "Problema persiste. Tempo de carregamento aumentou para 45 segundos",
  "prioridade": "CRITICA"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "titulo": "Sistema muito lento após atualização - URGENTE",
  "descricao": "Problema persiste. Tempo de carregamento aumentou para 45 segundos",
  "status": "ABERTA",
  "prioridade": "CRITICA",
  "dataCriacao": "2025-11-06T21:15:30",
  "dataAtualizacao": "2025-11-06T21:20:00"
}
```

---

### 6. Atualizar Status

**Request:**
```bash
PATCH http://localhost:8080/api/suporte/1/status
Content-Type: application/json

{
  "status": "EM_ANDAMENTO"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "titulo": "Sistema muito lento após atualização - URGENTE",
  "status": "EM_ANDAMENTO",
  "prioridade": "CRITICA",
  "dataAtualizacao": "2025-11-06T21:25:00"
}
```

---

### 7. Encerrar Solicitação

**Pré-requisito:** Status deve ser RESOLVIDA

**Request:**
```bash
PATCH http://localhost:8080/api/suporte/1/encerrar
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "titulo": "Sistema muito lento após atualização - URGENTE",
  "status": "FECHADA",
  "prioridade": "CRITICA",
  "dataAtualizacao": "2025-11-06T22:00:00"
}
```

---

### 8. Remover Solicitação

**Pré-requisito:** Status deve ser ABERTA

**Request:**
```bash
DELETE http://localhost:8080/api/suporte/5
```

**Response:** `204 No Content`

---

### 9. Obter Estatísticas

**Request:**
```bash
GET http://localhost:8080/api/suporte/estatisticas
```

**Response:** `200 OK`
```json
{
  "total": 10,
  "abertas": 3,
  "emAndamento": 4,
  "resolvidas": 2,
  "fechadas": 1
}
```

---

## ⚠️ Tratamento de Erros

A API utiliza um **tratamento centralizado de exceções** com `@ControllerAdvice`, retornando respostas padronizadas.

### Estrutura de Erro Padrão

```json
{
  "timestamp": "2025-11-06T21:30:00",
  "status": 400,
  "erro": "Erro de Validação",
  "mensagem": "Um ou mais campos contêm valores inválidos",
  "caminho": "/api/suporte",
  "erros": [
    {
      "campo": "titulo",
      "mensagem": "O título deve ter entre 5 e 100 caracteres"
    }
  ]
}
```

### Tipos de Erro

#### 1. Recurso Não Encontrado (404)

**Cenário:** Buscar solicitação inexistente
```bash
GET /api/suporte/999
```

**Response:**
```json
{
  "timestamp": "2025-11-06T21:30:00",
  "status": 404,
  "erro": "Recurso Não Encontrado",
  "mensagem": "Solicitação de suporte não encontrada com ID: 999",
  "caminho": "/api/suporte/999"
}
```

---

#### 2. Validação de Campos (400)

**Cenário:** Criar solicitação com dados inválidos
```bash
POST /api/suporte
{
  "titulo": "Ops",  // Muito curto!
  "descricao": "Erro"  // Muito curto!
}
```

**Response:**
```json
{
  "timestamp": "2025-11-06T21:30:00",
  "status": 400,
  "erro": "Erro de Validação",
  "mensagem": "Um ou mais campos contêm valores inválidos",
  "caminho": "/api/suporte",
  "erros": [
    {
      "campo": "titulo",
      "mensagem": "O título deve ter entre 5 e 100 caracteres"
    },
    {
      "campo": "descricao",
      "mensagem": "A descrição deve ter entre 10 e 500 caracteres"
    },
    {
      "campo": "prioridade",
      "mensagem": "A prioridade é obrigatória"
    }
  ]
}
```

---

#### 3. Transição de Status Inválida (400)

**Cenário:** Tentar transição não permitida
```bash
PATCH /api/suporte/1/status
{
  "status": "RESOLVIDA"  // Status atual: ABERTA
}
```

**Response:**
```json
{
  "timestamp": "2025-11-06T21:30:00",
  "status": 400,
  "erro": "Transição de Status Inválida",
  "mensagem": "Transição inválida: não é possível mudar de Aberta para Resolvida",
  "caminho": "/api/suporte/1/status"
}
```

---

#### 4. Regra de Negócio Violada (400)

**Cenário:** Tentar atualizar solicitação fechada
```bash
PUT /api/suporte/1
{
  "titulo": "Novo título"
}
```

**Response:**
```json
{
  "timestamp": "2025-11-06T21:30:00",
  "status": 400,
  "erro": "Erro de Regra de Negócio",
  "mensagem": "Não é possível atualizar uma solicitação já fechada",
  "caminho": "/api/suporte/1"
}
```

---

## 📡 Status Codes Utilizados

| Status Code | Significado | Quando Usar |
|-------------|-------------|-------------|
| **200 OK** | Sucesso | GET, PUT, PATCH bem-sucedidos |
| **201 Created** | Recurso criado | POST bem-sucedido |
| **204 No Content** | Sucesso sem conteúdo | DELETE bem-sucedido |
| **400 Bad Request** | Requisição inválida | Validação falhou, regra de negócio violada |
| **404 Not Found** | Recurso não encontrado | ID inexistente |
| **500 Internal Server Error** | Erro do servidor | Erro inesperado (logado) |

### Uso Correto de Métodos HTTP

| Método | Uso | Idempotente? | Safe? |
|--------|-----|--------------|-------|
| **GET** | Buscar recursos | ✅ Sim | ✅ Sim |
| **POST** | Criar recurso | ❌ Não | ❌ Não |
| **PUT** | Atualizar completo | ✅ Sim | ❌ Não |
| **PATCH** | Atualizar parcial | ⚠️ Depende | ❌ Não |
| **DELETE** | Remover recurso | ✅ Sim | ❌ Não |

---

## 💬 Perguntas Discursivas

### 1️⃣ O que diferencia uma API que "segue o protocolo HTTP" de uma API realmente "RESTful"?

#### **Análise Conceitual**

Muitas APIs afirmam ser "REST" simplesmente por utilizarem HTTP, mas essa é uma visão superficial. A diferença fundamental está na **aderência aos princípios arquiteturais REST** definidos por Roy Fielding em sua tese de doutorado.

#### **API que apenas "usa HTTP"**

Uma API que simplesmente usa HTTP como transporte pode:
- Usar apenas POST para todas as operações
- Não respeitar semântica de métodos HTTP
- Ter URIs inconsistentes (ex: `/getUser`, `/deleteUserById`)
- Retornar sempre 200 OK, mesmo em erros
- Manter estado no servidor entre requisições
- Não utilizar hypermedia (HATEOAS)

**Exemplo de API não-RESTful:**
```
POST /getUser         ❌ (deveria ser GET)
POST /deleteUser/123  ❌ (deveria ser DELETE)
GET /user/delete/123  ❌ (GET não deve modificar)

Resposta sempre:
200 OK { "error": true, "code": "USER_NOT_FOUND" }  ❌
```

#### **API verdadeiramente RESTful**

Uma API REST autêntica adere aos **6 princípios arquiteturais**:

##### **1. Cliente-Servidor (Client-Server)**
- Separação clara de responsabilidades
- Cliente cuida da UI, servidor dos dados
- Evolução independente de ambos os lados

##### **2. Stateless (Sem Estado)**
- Cada requisição é **auto-contida**
- Servidor não armazena contexto do cliente
- Toda informação necessária está na requisição
- Melhora escalabilidade (não precisa sincronizar sessões)

**Exemplo:**
```
❌ Stateful:
1. POST /login → servidor cria sessão
2. GET /perfil → servidor busca sessão

✅ Stateless:
1. POST /auth → retorna token JWT
2. GET /perfil (Authorization: Bearer token) → valida token
```

##### **3. Cache**
- Respostas devem indicar se são cacheáveis
- Uso de headers: `Cache-Control`, `ETag`, `Last-Modified`
- Melhora performance e reduz carga do servidor

##### **4. Interface Uniforme**
Composta por 4 sub-restrições:

**a) Identificação de Recursos**
```
✅ /api/suporte/123         (recurso identificado por URI)
❌ /api/getSolicitacao?id=123  (verbo na URI)
```

**b) Manipulação através de Representações**
```
Cliente manipula recursos através de JSON, XML, etc.
Não acessa diretamente o banco de dados
```

**c) Mensagens Auto-descritivas**
```
Content-Type: application/json
Accept: application/json
HTTP/1.1 201 Created
Location: /api/suporte/123
```

**d) HATEOAS** (Hypermedia as the Engine of Application State)
```json
{
  "id": 1,
  "status": "ABERTA",
  "_links": {
    "self": "/api/suporte/1",
    "atualizar": "/api/suporte/1",
    "mudar-status": "/api/suporte/1/status"
  }
}
```

##### **5. Sistema em Camadas**
- Cliente não sabe se está conectado ao servidor final
- Permite load balancers, proxies, gateways
- Melhora escalabilidade e segurança

##### **6. Código sob Demanda (Opcional)**
- Servidor pode enviar código executável (JavaScript)
- Único princípio opcional

#### **Comparação Prática**

| Aspecto | HTTP Simples | RESTful |
|---------|--------------|---------|
| **URIs** | `/getUsers`, `/createUser` | `/users`, `/users/{id}` |
| **Métodos** | Tudo POST | GET, POST, PUT, DELETE semânticos |
| **Status** | Sempre 200 | 200, 201, 204, 400, 404, 500 |
| **Estado** | Sessões no servidor | Stateless, token na requisição |
| **Cache** | Ignorado | Headers apropriados |
| **Docs** | Ad-hoc | OpenAPI, HATEOAS |

#### **Conclusão**

Uma API RESTful não é apenas sobre usar HTTP, mas sobre **abraçar uma filosofia arquitetural** que promove:
- 🎯 **Uniformidade** - Desenvolvedores sabem o que esperar
- 📈 **Escalabilidade** - Stateless permite distribuição horizontal
- 🔄 **Evolvibilidade** - Versionamento e mudanças controladas
- 🚀 **Performance** - Cache e otimizações nativas HTTP
- 🛠️ **Manutenibilidade** - Código limpo e previsível

---

### 2️⃣ Por que manter consistência em Status Codes, URIs e Mensagens de Erro em ecossistemas com múltiplos serviços?

#### **Contexto: Arquitetura de Microserviços**

Em sistemas modernos, raramente temos apenas uma API. Geralmente temos **dezenas ou centenas de serviços** interagindo:

```
┌─────────────┐      ┌─────────────┐      ┌─────────────┐
│   API User  │─────▶│  API Order  │─────▶│ API Payment │
└─────────────┘      └─────────────┘      └─────────────┘
       │                    │                     │
       ▼                    ▼                     ▼
┌─────────────┐      ┌─────────────┐      ┌─────────────┐
│  API Auth   │      │ API Notify  │      │  API Log    │
└─────────────┘      └─────────────┘      └─────────────┘
```

#### **1. Experiência do Desenvolvedor (Developer Experience - DX)**

##### **Cenário Sem Consistência** ❌

```javascript
// API de Usuários
POST /users
201 Created
{ "user_id": 123 }

// API de Pedidos (INCONSISTENTE!)
POST /orders
200 OK  ❌ (deveria ser 201!)
{ "orderId": 456 }  ❌ (camelCase vs snake_case!)

// API de Pagamentos (PIOR AINDA!)
POST /payments
200 OK  ❌
{ "success": true, "payment": { "id": 789 } }  ❌ (estrutura diferente!)
```

**Problemas:**
- ❌ Desenvolvedor precisa **memorizar particularidades** de cada API
- ❌ Código cliente cheio de `if/else` para cada serviço
- ❌ **Curva de aprendizado** aumenta exponencialmente
- ❌ Onboarding de novos desenvolvedores mais lento
- ❌ Mais bugs por expectativas incorretas

##### **Cenário Com Consistência** ✅

```javascript
// Todas as APIs seguem o mesmo padrão
POST /users
201 Created
Location: /users/123
{ "id": 123, "nome": "João" }

POST /orders
201 Created
Location: /orders/456
{ "id": 456, "total": 100.00 }

POST /payments
201 Created
Location: /payments/789
{ "id": 789, "valor": 100.00 }
```

**Benefícios:**
- ✅ **Uma vez aprendido, aplica-se a todos** os serviços
- ✅ Código cliente genérico e reutilizável
- ✅ Desenvolvedores **mais produtivos**
- ✅ Menos erros, menos frustração

#### **2. Escalabilidade e Manutenção**

##### **Problema: Tratamento de Erros Inconsistente**

```javascript
// API 1 retorna erros assim:
{
  "error": "User not found",
  "code": 404
}

// API 2 retorna assim:
{
  "message": "Resource does not exist",
  "status": "NOT_FOUND",
  "timestamp": "2025-11-06T21:00:00Z"
}

// API 3 retorna assim:
{
  "errors": [
    { "field": "id", "issue": "not found" }
  ]
}
```

**Cliente precisa tratar 3 formatos diferentes:**

```javascript
// Código cliente HORRÍVEL:
try {
  const response = await fetch(url);
  const data = await response.json();
  
  // Tratamento diferente para cada API ❌
  if (data.error) {
    // API 1
  } else if (data.message) {
    // API 2
  } else if (data.errors) {
    // API 3
  }
} catch (e) {
  // E se houver mais APIs?
}
```

##### **Solução: Padrão Unificado**

```javascript
// Todas as APIs usam RFC 7807 (Problem Details)
{
  "type": "https://api.example.com/erros/recurso-nao-encontrado",
  "title": "Recurso Não Encontrado",
  "status": 404,
  "detail": "Solicitação de suporte não encontrada com ID: 999",
  "instance": "/api/suporte/999",
  "timestamp": "2025-11-06T21:00:00Z"
}
```

**Cliente pode ter código genérico:**

```javascript
// MUITO MELHOR! ✅
async function handleApiCall(url) {
  try {
    const response = await fetch(url);
    
    if (!response.ok) {
      const error = await response.json();
      // Tratamento unificado para TODAS as APIs
      showError(error.title, error.detail);
    }
    
    return await response.json();
  } catch (e) {
    handleNetworkError(e);
  }
}
```

#### **3. Observabilidade e Debugging**

##### **Logs Consistentes Facilitam Troubleshooting**

```
❌ Logs inconsistentes:
[API-1] ERROR: User 123 failed
[API-2] [500] Internal error in payment processing
[API-3] Payment gateway returned error code XYZ

Qual é o problema real? Difícil correlacionar! ❌
```

```
✅ Logs padronizados:
[API-User] [404] GET /users/123 - User not found
[API-Order] [201] POST /orders - Order created: 456
[API-Payment] [400] POST /payments - Invalid card number

Fácil rastrear o fluxo e encontrar o erro! ✅
```

#### **4. Automação e Ferramentas**

##### **APIs Consistentes permitem:**

```yaml
# Configuração de Monitoring (Prometheus/Grafana)
- alert: HighErrorRate
  expr: http_requests_total{status=~"5.."} > 100
  # ✅ Funciona para TODAS as APIs!

- alert: NotFoundErrors
  expr: http_requests_total{status="404"} > 50
  # ✅ Padrão único para todas!
```

##### **Geração Automática de Clientes**

```bash
# OpenAPI permite gerar clientes automaticamente
openapi-generator generate -i api1.yaml -o client/
openapi-generator generate -i api2.yaml -o client/

# ✅ Se todas seguem o padrão, clientes são consistentes!
```

#### **5. Governança e Compliance**

Em grandes empresas:

```
┌────────────────────────────────────────┐
│   API Governance Board                 │
│                                        │
│  - Define padrões obrigatórios         │
│  - Status codes: RFC 7231              │
│  - Erros: RFC 7807                     │
│  - Naming: kebab-case                  │
│  - Versionamento: /v1/, /v2/           │
└────────────────────────────────────────┘
          │
          ▼
┌──────────────────────────────────────────┐
│  API Gateway valida conformidade        │
│  - Rejeita APIs fora do padrão          │
│  - Força consistência automaticamente   │
└──────────────────────────────────────────┘
```

#### **6. Impacto no Longo Prazo**

| Aspecto | Sem Consistência | Com Consistência |
|---------|------------------|------------------|
| **Tempo de Integração** | Dias/Semanas | Horas |
| **Bugs de Integração** | Alto | Baixo |
| **Documentação Necessária** | Extensa para cada API | Padrão único |
| **Onboarding de Devs** | Lento (dias) | Rápido (horas) |
| **Manutenção** | Cada API é única | Padrões compartilhados |
| **Tooling** | Específico por API | Reutilizável |
| **Custos** | 💰💰💰 Alto | 💰 Baixo |

#### **Conclusão**

Manter consistência em Status Codes, URIs e Mensagens de Erro não é **"nice to have"**, é **essencial** porque:

1. 🧑‍💻 **Developer Experience** - Devs são mais produtivos e felizes
2. 📈 **Escalabilidade** - Sistemas crescem sem virar caos
3. 🔍 **Observabilidade** - Problemas são encontrados rapidamente
4. 🤖 **Automação** - Ferramentas funcionam uniformemente
5. 💰 **Custo** - Menos tempo gasto em problemas de integração
6. 🛡️ **Qualidade** - Menos bugs, mais confiabilidade

**Em resumo:** Consistência é a diferença entre um ecossistema de serviços **gerenciável** e um **pesadelo de manutenção**.

---

## 👥 Contribuidores

- **Desenvolvedor**: [Seu Nome]
- **Turma**: [Turma FIAP]
- **Disciplina**: Arquitetura Orientada a Serviço
- **Professor**: [Nome do Professor]
- **Data**: Novembro/2025

---

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais como parte do Checkpoint 3 da disciplina de Arquitetura Orientada a Serviço da FIAP.

---

## 🔗 Links Úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [REST Architectural Constraints](https://restfulapi.net/rest-architectural-constraints/)
- [RFC 7231 - HTTP/1.1 Semantics](https://tools.ietf.org/html/rfc7231)
- [RFC 7807 - Problem Details for HTTP APIs](https://tools.ietf.org/html/rfc7807)
- [OpenAPI Specification](https://swagger.io/specification/)

---

<div align="center">

**Desenvolvido com ☕ e 💻 por estudantes FIAP**

[⬆ Voltar ao topo](#-sistema-de-suporte-técnico---api-restful)

</div>
