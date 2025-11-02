# 🚀 Projeto 2-3/30: Content-Catalog-API

**Conceito:** Microsserviço de Catálogo de Conteúdo (Inspirado em Netflix/Spotify)

## 🎯 Business Plan & Propósito

Este microsserviço simula a API de catálogo de conteúdo de plataformas de streaming (Netflix, Spotify). Seu valor de negócio reside em oferecer **busca eficiente**, **paginação otimizada** e **filtros avançados** para milhões de itens de conteúdo (filmes, séries, músicas).

**Escalabilidade:** Projetado para lidar com grandes volumes de dados usando **paginação eficiente** que evita Out of Memory (OOM), permitindo buscar milhões de registros sem sobrecarregar a memória.

## 🛠️ Stack Tecnológica

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.2+
- **Dependências:** Spring Web, Spring Data JPA, Validation, Actuator
- **Banco de Dados:** PostgreSQL 15
- **Documentação:** OpenAPI/Swagger
- **Containerização:** Docker
- **Testes:** JUnit 5, Mockito, Testcontainers
- **CI/CD:** GitHub Actions

## 🏗️ Arquitetura e Boas Práticas

### Paginação Eficiente (Under the Hood)

Uso de `Pageable` do Spring Data JPA que gera SQL com `LIMIT` e `OFFSET`, retornando apenas os registros necessários do banco, não carregando tudo em memória.

**Impacto:**
- **Memória:** 1M objetos → 20 objetos (99.998% menos!)
- **Performance:** Query otimizada (muito mais rápido)
- **Escalabilidade:** Funciona com 10 ou 10 milhões de registros

### Busca Avançada com Filtros

Query dinâmica usando `@Query` que combina múltiplos filtros (título, tipo, gênero, ano, rating) de forma eficiente.

### DTOs (Data Transfer Objects)

Separação entre entidade JPA (`Content`) e objetos de transferência (`ContentCreateDTO`, `ContentResponseDTO`, `SearchRequestDTO`).

## 👨‍💻 Como Rodar o Projeto

### Pré-requisitos

- Java 21
- Maven 3.8+
- Docker e Docker Compose

### Passo a Passo

1. **Subir o banco de dados:**
   ```bash
   cd content-catalog-api
   docker-compose up -d
   ```

2. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Ou:
   ```bash
   mvn spring-boot:run
   ```

3. **Teste os endpoints:**
   ```bash
   # Criar conteúdo
   curl -X POST http://localhost:8081/api/content \
     -H "Content-Type: application/json" \
     -d '{
       "title": "The Matrix",
       "description": "A hacker learns about reality",
       "contentType": "MOVIE",
       "genre": "Sci-Fi",
       "releaseYear": 1999,
       "rating": 8.7,
       "durationMinutes": 136
     }'
   
   # Listar com paginação
   curl http://localhost:8081/api/content?page=0&size=20
   
   # Buscar com filtros
   curl -X POST http://localhost:8081/api/content/search \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Matrix",
       "genre": "Sci-Fi",
       "minRating": 8.0
     }'
   ```

## 📊 Endpoints da API

### Conteúdo
- `GET /api/content` - Listar todo conteúdo (com paginação)
- `GET /api/content/{id}` - Buscar conteúdo por ID
- `POST /api/content` - Criar novo conteúdo
- `PUT /api/content/{id}` - Atualizar conteúdo
- `DELETE /api/content/{id}` - Deletar conteúdo

### Busca
- `POST /api/content/search` - Buscar com filtros avançados (título, tipo, gênero, ano, rating) e paginação

## 🧪 Executar Testes

```bash
# Todos os testes
./mvnw test

# Com cobertura
./mvnw test jacoco:report

# Ver relatório de cobertura
open target/site/jacoco/index.html
```

## 📈 Métricas de Sucesso

- **Latência:**** Tempo de resposta da API de busca < 200ms (p95)
- **Qualidade:** Cobertura de testes ≥ 80%
- **Performance:** Paginação retorna apenas 20 registros por vez (evita OOM)
- **Escalabilidade:** Suporta milhões de registros sem degradação

## 🔗 Links Úteis

- **Swagger/OpenAPI:** http://localhost:8081/swagger-ui.html
- **API Docs:** http://localhost:8081/api-docs
- **Actuator:** http://localhost:8081/actuator/health

## 📝 Documentação

Veja `Business_Plan.md` para detalhes sobre decisões de arquitetura e justificativas técnicas, especialmente sobre paginação e performance.

## 🎓 Conceitos Aprendidos

### Paginação vs. Busca Completa

**Problema:** Buscar 1 milhão de registros em memória causa OutOfMemoryError.

**Solução:** Paginação com `Pageable` gera SQL `LIMIT/OFFSET`, retornando apenas os registros necessários.

```java
// ❌ Errado: Busca tudo
List<Content> all = repository.findAll(); // 1M objetos!

// ✅ Correto: Paginação
Page<Content> page = repository.findAll(PageRequest.of(0, 20));
// Apenas 20 objetos na memória!
```

### Busca Eficiente com Índices

Uso de `@Index` na entidade para otimizar queries por `contentType`, `genre` e `rating`.

---

**#30DiasJava | #SpringBoot | #Microsserviços | #CleanCode | #Performance | #Pagination**

