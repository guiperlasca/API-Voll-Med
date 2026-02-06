# 🏥 Voll.med API

A **Voll.med API** é o back-end de uma aplicação robusta para **gestão de clínicas médicas**, permitindo o gerenciamento completo de **médicos**, **pacientes** e **consultas**.  
O projeto foi desenvolvido seguindo **boas práticas de engenharia de software**, aplicando princípios **REST**, **SOLID**, validações de regras de negócio e persistência de dados eficiente.

---

## 🚀 Funcionalidades

### 🩺 Médicos
- **Cadastro** de médicos com:
  - Nome
  - E-mail
  - CRM
  - Especialidade (Ortopedia, Cardiologia, Ginecologia e Dermatologia)
  - Endereço completo
- **Listagem** paginada e ordenada por nome (apenas médicos ativos)
- **Atualização** de dados cadastrais e endereço
- **Exclusão lógica**, mantendo o histórico no banco de dados

---

### 👤 Pacientes
- **CRUD completo**:
  - Cadastro
  - Listagem
  - Atualização
  - Exclusão lógica

---

### 📅 Consultas
- **Agendamento de consultas** com validações de regras de negócio
- **Regras aplicadas**:
  - ⏱️ Antecedência mínima de **30 minutos**
  - 🕖 Horário de funcionamento da clínica:
    - **Segunda a Sábado**, das **07h às 19h**
  - 🚫 Bloqueio de agendamento com médicos ou pacientes **inativos**
  - 🎲 **Escolha automática de médico aleatório** quando não informado no agendamento

---

## 🛠️ Tecnologias Utilizadas

- **Java 21** — Versão LTS mais recente
- **Spring Boot 3.5.4** — Framework principal da aplicação
- **Spring Data JPA** — Persistência e abstração do acesso a dados
- **Flyway** — Versionamento e migração do banco de dados
- **Spring Security + JWT** — Autenticação stateless e proteção de endpoints
- **Jakarta Validation** — Validação de dados via DTOs (Records)
- **Lombok** — Redução de código boilerplate
- **H2 Database** — Banco em memória para testes
- **MySQL** — Banco de dados para ambiente de produção
- **SpringDoc OpenAPI (Swagger)** — Documentação interativa da API

---

## ⚙️ Como Executar o Projeto

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/guiperlasca/api-voll-med.git
```

### 2️⃣ Configurar variáveis de ambiente
A aplicação utiliza uma **chave secreta para o JWT**.  
Configure no `application.properties` ou como variável de sistema:

```properties
JWT_SECRET=sua_chave_secreta_aqui
```

### 3️⃣ Executar a aplicação
Utilizando o **Maven Wrapper** incluso no projeto:

```bash
./mvnw spring-boot:run
```

---

## 📘 Documentação da API

Com a aplicação em execução, acesse o **Swagger UI**:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Testes Automatizados

O projeto conta com:
- **Testes de integração**
- **Testes de repositório**
- Validação das regras de negócio

### Executar os testes
```bash
./mvnw test
```

---

## ✒️ Autor

**Guilherme Perlasca**  
🎓 Estudante de **Ciência da Computação** na **UniRitter**  
💻 Focado em **desenvolvimento back-end** com **Java** e **Spring Boot**

---

## 📄 Licença
Este projeto é destinado a fins educacionais e de portfólio.
