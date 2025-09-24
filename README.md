
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
  "placa": "ABC1230",
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
  "placa": "ABC1230",
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
  "identificador": "VAGA-A01",
  "codigo": "A123",
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
  "identificador": "VAGA-A01",
  "codigo": "A01",
  "patioId": 1
}
```

#### DELETE /api/vagas/{id}`
```http
http://localhost:8080/api/vagas/1
```

### 📦 Aluguel

#### POST `/api/aluguel`
```json
{
  "motoId": 1,
  "clienteId": 2,
  "dataInicio": "2025-09-23",
  "dataFim": "2025-09-30"
}
```

#### GET `/api/aluguel`
```http
http://localhost:8080/api/aluguel
```

#### GET `/api/aluguel/{id}`
```http
http://localhost:8080/api/aluguel/1
```

#### PUT `/api/aluguel/{id}`
{
  "dataFim": "2025-10-05"
}

#### DELETE `/api/aluguel/{id}`
```http
http://localhost:8080/api/aluguel/1
```

### 🛠️ Manutenção

#### POST `/api/manutencao`
```json
{
  "motoId": 1,
  "descricao": "Troca de óleo",
  "data": "2025-09-20"
}
```

#### GET `/api/manutencao`
```http
http://localhost:8080/api/manutencao
```

#### GET `/api/manutencao/{id}`
```http
http://localhost:8080/api/manutencao/1
```

#### PUT `/api/manutencao/{id}`
```json
{
  "descricao": "Revisão geral",
  "data": "2025-09-25"
}
```

#### DELETE `/api/manutencao/{id}`
```http
http://localhost:8080/api/manutencao/1
```

### 👤 Usuários

#### POST `/api/users`
```json
{
  "username": "Admin da Silva",
  "email": "adminsilva@email.com",
  "password": "SenhaSegura",
  "role": "ADMIN"
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
  "username": "Admin 2",
  "email": "adminsilva@email.com",
  "password": "SenhaSegura",
  "role": "ADMIN"
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
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Cancelar aluguel

### 🛠️ Manutenção (`/api/manutencoes`)
- `POST` - Registrar manutenção
- `GET` - Listar todas
- `GET /{id}` - Buscar por ID
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Remover manutenção

### 👤 Usuários (`/api/users`)
- `POST` - Criar usuário
- `GET` - Listar todos
- `GET /{id}` - Buscar por ID
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Deletar

### 🔐 Autenticação
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Autenticar e obter token JWT
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

## 🧠 Futuras Melhorias

- ✅ **Criar dashboard visual** com Spring + React para supervisão dos pátios em tempo real;
- ✅ **Integrar com API externa de leitura de motos** (fornecida pela Mottu ou terceiros) para automatizar o processo de entrada e saída, substituindo o envio manual da placa via request.
- ✅ **Deixar todos os campos em ingles**
