
# 🏍️ Mottu Pátio - API de Controle Automatizado de Motos

## 🙍‍♂️🙍‍♂️ Integrantes
Henzo Boschiero Puchetti - Rm555179

Luann Domingos Mariano - Rm558548

Caio Cesar Rosa Nyimi - Rm556331

## 📘 Descrição do Projeto

Este projeto é uma API REST desenvolvida em Java com Spring Boot para automatizar o controle de entrada, saída e posicionamento de motocicletas nos pátios da Mottu. A solução tem como objetivo eliminar processos manuais e tornar a gestão dos pátios mais eficiente, segura e escalável.

---

## ❗ Problema

A Mottu gerencia centenas de motos em pátios espalhados pelo Brasil e México. O controle atual, feito de forma manual por operadores, é suscetível a falhas humanas, dificulta a localização das motos e compromete a produtividade. Além disso, a ausência de rastreabilidade e visibilidade em tempo real impacta diretamente a operação e a experiência dos entregadores.

---

## ✅ Solução

A API proposta integra um sistema com tecnologia que permite:

- Registro da entrada e saída de motos;
- Mapeamento e rastreamento das vagas em tempo real;
- Visibilidade completa da situação dos pátios;
- Integração com o sistema interno da Mottu para vincular motos a operadores.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.5**
  - Spring Web
  - Spring Data JPA
  - Bean Validation
  - Spring Cache
  - Thymeleaf
  - Flyway
  - Spring Security
- **Banco de Dados H2 (em memória)**
- **Lombok**
- **MapStruct**
- **Maven**

---

## 📁 Estrutura de Pacotes

```
com.fiap.mottu_patio
├── auth
├── config
├── controller
├── dto
├── exception
├── mapper
├── model
├── repository
├── security
├── service
└── specification
```

---

## 🧪 Exemplos de Requisições (via Postman)

### 🔐 Autenticação

#### POST `/api/auth/register`
```json
{
  "username": "Admin da Silva",
  "email": "adminsilva@email.com",
  "password": "SenhaSegura",
  "role": "ADMIN"
}
```

#### POST `/api/auth/login`
```json
{
  "email": "adminsilva@email.com",
  "password": "SenhaSegura"
}
```
Resposta:
```json
{
    "message": "Login realizado com sucesso"
}
```

### 🏍️ Motos

#### POST `/api/motos`
```json
{
  "placa": "ABC-1230",
  "modelo": "MOTTU_SPORT",
  "ano": 2023,
  "quilometragem": 15000,
  "status": "DISPONIVEL",
  "patioId": 1
}
```

#### GET `/api/motos`
```http
http://localhost:8080/api/motos
```

#### GET `/api/motos/{id}`
```http
http://localhost:8080/api/motos/1
```

#### PUT `/api/motos/{id}`
```json
{
  "placa": "ABC-1230",
  "modelo": "MOTTU_E",
  "ano": 2023,
  "quilometragem": 15000,
  "status": "DISPONIVEL",
  "patioId": 1
}
```

#### DELETE `/api/motos/{id}`
```http
http://localhost:8080/api/motos/1
```

### 📦 Pátios

#### POST `/api/patios`
```json
{
  "nome": "Patio TOP",
  "endereco": "Av. Interlagos, 1200 - Zona Sul",
  "capacidade": 50
}
```

#### GET /api/patios`
```http
http://localhost:8080/api/patios
```

#### GET `/api/patios/{id}`
```http
http://localhost:8080/api/patios/1
```

#### PUT `/api/patios/{id}`
```json
{
  "nome": "Patio Atualizado",
  "endereco": "Av. Interlagos, 1200 - Zona Sul",
  "capacidade": 50
}
```

#### DELETE `/api/patios/{id}`
```http
http://localhost:8080/api/patios/1
```

### 🧠 Vagas

#### POST /api/vagas`
```json
{
  "identificador": "VAGA-TESTE",
  "codigo": "TESTE",
  "patioId": 1
}
```

#### GET `/api/vagas`
```http
http://localhost:8080/api/vagas
```

#### GET /api/vagas/{id}`
```http
http://localhost:8080/api/vagas/1
```

#### PUT /api/vagas/{id}`
```json
{
  "identificador": "VAGA-TESTEATUALIZADO",
  "codigo": "TESTE",
  "patioId": 1
}
```

#### DELETE /api/vagas/{id}`
```http
http://localhost:8080/api/vagas/1
```

### 📦 Aluguel

#### POST `/api/alugueis`
```json
{
  "userId": 1,
  "motoId": 1,
  "startDate": "2025-09-23T00:00:00",
  "endDate": "2025-09-30T00:00:00",
  "status": "ALUGADA"
}
```

#### GET `/api/alugueis`
```http
http://localhost:8080/api/alugueis
```

#### GET `/api/alugueis/{id}`
```http
http://localhost:8080/api/alugueis/1
```

#### DELETE `/api/alugueis/{id}`
```http
http://localhost:8080/api/alugueis/1
```

### 🛠️ Manutenção

#### POST `/api/manutencoes`
```json
{
  "motoId": 1,
  "descricao": "Troca de óleo",
  "data": "2025-09-20T00:00:00"
}
```

#### GET `/api/manutencoes`
```http
http://localhost:8080/api/manutencoes
```

#### GET `/api/manutencoes/{id}`
```http
http://localhost:8080/api/manutencoes/1
```

#### DELETE `/api/manutencoes/{id}`
```http
http://localhost:8080/api/manutencoes/1
```

### 👤 Usuários

#### POST `/api/users`
```json
{
  "username": "Usuario Comum",
  "email": "comumuser@email.com",
  "password": "SenhaSegura",
  "role": "CLIENTE"
}
```

#### GET `/api/users`
```http
http://localhost:8080/api/users
```

#### GET `/api/users/{id}`
```http
http://localhost:8080/api/users/1
```

#### PUT `/api/users/{id}`
```json
{
  "username": "Usuario Comum e Atualizado",
  "email": "comumuser@email.com",
  "password": "SenhaSegura",
  "role": "CLIENTE"
}
```

#### DELETE `/api/users/{id}`
```http
http://localhost:8080/api/users/1
```


### ✨ Endpoints

### 📦 Motos (`/api/motos`)
- `POST` - Criar moto
- `GET` - Listar todas
- `GET /{id}` - Buscar por ID
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Deletar

### 🏟️ Pátios (`/api/patios`)
- `POST` - Criar pátio (com geração automática de vagas)
- `GET` - Listar todos
- `GET /{id}` - Buscar por ID
- `PUT /{id}` - Atualizar (inclusive vagas)
- `DELETE /{id}` - Deletar

### 📊 Vagas (`/api/vagas`)
- `POST` - Criar vaga
- `GET` - Listar todos
- `GET /{id}` - Buscar por ID
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Deletar

### 📦 Aluguel (`/api/alugueis`)
- `POST` - Criar aluguel
- `GET` - Listar todos
- `GET /{id}` - Buscar por ID
- `DELETE /{id}` - Cancelar aluguel

### 🛠️ Manutenção (`/api/manutencoes`)
- `POST` - Registrar manutenção
- `GET` - Listar todas
- `GET /{id}` - Buscar por ID
- `DELETE /{id}` - Remover manutenção

### 👤 Usuários (`/api/users`)
- `POST` - Criar usuário
- `GET` - Listar todos
- `GET /{id}` - Buscar por ID
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Deletar

### 🔐 Autenticação
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Autenticar e obter acesso aos CRUDS
- 
---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos:
- Java 17 instalado
- Maven instalado

### Passos:

```bash
# Clone o repositório
git clone https://github.com/CaiocrNyimi/API-Mottu.git
cd API-Mottu

# Compile o projeto
mvn clean install

# Rode a aplicação
mvn spring-boot:run
```

A API estará disponível em:  
📍 `http://localhost:8080`


Ou...

Acesse direto na nuvem em:
📍 `https://api-mottu-bhcx.onrender.com`