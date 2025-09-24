
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

## 🔌 Endpoints Principais
---

## 🔄 Fluxo Esperado da API

1. **Cadastro de Pátio**  
   - Ao cadastrar um novo pátio com as informações corretas (nome, endereço, capacidade), o sistema **gera automaticamente as vagas** com base na capacidade informada.

2. **Cadastro de Moto**  
   - Uma moto deve ser cadastrada com os dados corretos (placa, modelo, cor, ano) e o **ID do pátio** onde ela ficará estacionada.

3. **Evento LPR - Entrada**  
   - Ao registrar um evento do tipo `ENTRADA`, passando a **placa da moto** e a **vaga que ela ocupou**, o sistema:
     - Busca pela moto com base na placa informada;
     - Associa a vaga à moto encontrada;
     - Altera o status da vaga para **ocupada**;
     - Reduz o número de **vagas disponíveis** no pátio correspondente.

4. **Evento LPR - Saída**  
   - Ao registrar um evento do tipo `SAIDA` com a placa da moto:
     - O sistema desassocia a vaga da moto;
     - Altera o status da vaga para **livre**;
     - Aumenta o número de **vagas disponíveis** no pátio.

## 🧪 Exemplos de Requisições (via Postman)

### 🔐 Autenticação

#### POST `/api/auth/register`
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "tipoUsuario": "CLIENTE"
}
```

#### POST `/api/auth/login`
```json
{
  "email": "joao@email.com",
  "senha": "123456"
}
```
Resposta:
```json
{
  "token": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 🏍️ Motos

#### POST `/api/motos`
```json
{
  "placa": "ABC1234",
  "modelo": "Honda azul",
  "cor": "azul",
  "ano": 2025,
  "patioId": 1
}
```

#### GET `/api/motos`
```http
http://localhost:8080/motos
```

#### GET `/api/motos/{id}`
```http
http://localhost:8080/motos/1
```

#### PUT `/api/motos/{id}`
```json
{
  "placa": "XYZ5678",
  "modelo": "XJ6",
  "cor": "preta",
  "ano": 2024,
  "patioId": 2
}
```

#### DELETE `/api/motos/{id}`
```http
http://localhost:8080/motos/1
```

### 📦 Pátios

#### POST `/api/patios`
```json
{
  "nome": "Pátio Central",
  "endereco": "Rua das Motos, 123",
  "capacidade": 40
}
```

#### GET /api/patios`
```http
http://localhost:8080/patios
```

#### GET `/api/patios/{id}`
```http
http://localhost:8080/patios/1
```

#### PUT `/api/patios/{id}`
```json
{
  "nome": "Pátio Atualizado",
  "endereco": "Rua Nova, 456",
  "capacidade": 50
}
```

#### DELETE `/api/patios/{id}`
```http
http://localhost:8080/patios/1
```

### 🧠 Vagas

#### POST /api/vagas`
```json
{
  "codigo": "A:1",
  "identificador": "Vaga A1",
  "patioId": 1
}
```

#### GET `/api/vagas`
```http
http://localhost:8080/vagas
```

#### GET /api/vagas/{id}`
```http
http://localhost:8080/vagas/1
```

#### PUT /api/vagas/{id}`
```json
{
  "codigo": "B:2",
  "identificador": "Vaga B2",
  "patioId": 1
}
```

#### DELETE /api/vagas/{id}`
```http
http://localhost:8080/vagas/1
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
http://localhost:8080/aluguel
```

#### GET `/api/aluguel/{id}`
```http
http://localhost:8080/aluguel/1
```

#### PUT `/api/aluguel/{id}`
{
  "dataFim": "2025-10-05"
}

#### DELETE `/api/aluguel/{id}`
```http
http://localhost:8080/aluguel/1
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
http://localhost:8080/manutencao
```

#### GET `/api/manutencao/{id}`
```http
http://localhost:8080/manutencao/1
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
http://localhost:8080/manutencao/1
```

### 👤 Usuários

#### POST `/api/users`
```json
{
  "username": "joaosilva",
  "password": "senha123",
  "role": "CLIENTE"
}
```

#### GET `/api/users`
```http
http://localhost:8080/users
```

#### GET `/api/users/{id}`
```http
http://localhost:8080/users/1
```

#### PUT `/api/users/{id}`
```json
{
  "username": "joaosilva_atualizado",
  "password": "novaSenha456",
  "role": "ADMIN"
}
```

#### DELETE `/api/users/{id}`
```http
http://localhost:8080/users/1
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
