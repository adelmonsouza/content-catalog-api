# ✅ Validar e Testar - Content-Catalog-API (Dia 2)

**Status:** ✅ Projeto 100% Completo e Pronto!

---

## 🚀 Teste Rápido (3 Passos)

### 1. Subir o Banco de Dados

```bash
cd /Volumes/AdellServer/Projects/30days/30DiasJava/content-catalog-api
docker-compose up -d
```

**Verificar:**
```bash
docker ps | grep postgres
# Deve mostrar: content-catalog-postgres
```

---

### 2. Executar a Aplicação

```bash
# Opção A: Maven Wrapper
./mvnw spring-boot:run

# Opção B: Maven Global
mvn spring-boot:run
```

**✅ Sucesso se ver:**
```
Started ContentCatalogApiApplication in X.XXX seconds
```

**Aplicação rodando em:** `http://localhost:8081`

---

### 3. Testar Endpoints

**Swagger UI (Interface Visual):**
```
http://localhost:8081/swagger-ui.html
```

**Ou via cURL:**

```bash
# 1. Criar conteúdo
curl -X POST http://localhost:8081/api/content \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Matrix",
    "description": "A hacker learns about the true nature of reality",
    "contentType": "MOVIE",
    "genre": "Sci-Fi",
    "releaseYear": 1999,
    "rating": 8.7,
    "durationMinutes": 136
  }'

# 2. Listar com paginação
curl http://localhost:8081/api/content?page=0&size=20

# 3. Buscar por ID
curl http://localhost:8081/api/content/1

# 4. Busca avançada
curl -X POST http://localhost:8081/api/content/search \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Matrix",
    "genre": "Sci-Fi",
    "minRating": 8.0
  }'
```

---

## 🧪 Executar Testes

```bash
# Todos os testes
./mvnw test

# Com cobertura
./mvnw test jacoco:report

# Ver relatório
open target/site/jacoco/index.html
```

**✅ Esperado:**
```
Tests run: X, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## 📊 Endpoints Disponíveis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/content` | Criar conteúdo |
| GET | `/api/content` | Listar (com paginação) |
| GET | `/api/content/{id}` | Buscar por ID |
| PUT | `/api/content/{id}` | Atualizar |
| DELETE | `/api/content/{id}` | Deletar |
| POST | `/api/content/search` | Busca avançada com filtros |

---

## ✅ Checklist de Validação

- [ ] PostgreSQL rodando (`docker ps`)
- [ ] Aplicação iniciada sem erros
- [ ] Swagger UI acessível (`http://localhost:8081/swagger-ui.html`)
- [ ] Endpoint POST `/api/content` cria conteúdo
- [ ] Endpoint GET `/api/content` lista com paginação
- [ ] Endpoint POST `/api/content/search` busca com filtros
- [ ] Testes passando (`./mvnw test`)

---

## 🐛 Troubleshooting

### Erro: "Port 5433 already in use"
```bash
# O projeto usa porta 5433 (diferente do Dia 1 que usa 5432)
# Verificar:
lsof -i :5433

# Ou parar containers:
docker-compose down
```

### Erro: "Cannot connect to database"
```bash
# Verificar se PostgreSQL está rodando
docker ps | grep postgres

# Ver logs:
docker logs content-catalog-postgres

# Subir novamente:
docker-compose up -d
```

---

## 📋 Próximo: Commitar no GitHub

Após validar que tudo funciona:

```bash
cd /Volumes/AdellServer/Projects/30days/30DiasJava

# Ver status
git status

# Adicionar projeto Dia 2
git add content-catalog-api/

# Commit
git commit -m "feat: Implementar Content-Catalog-API (Dia 2-3) - Paginação e busca avançada"

# Push
git push origin main
```

---

**✅ Tudo pronto para testar! 🚀**

