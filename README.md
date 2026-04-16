# 🕷️ Web Scraping - Price Monitor Bot

Um aplicativo Spring Boot robusto que monitora preços de produtos em websites e notifica usuários via **Telegram** quando há redução de preço. Combina web scraping com agendamento automático para oferecer uma solução completa de monitoramento de preços.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Setup](#instalação-e-setup)
- [Configuração](#configuração)
- [Como Usar](#como-usar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [API e Endpoints](#api-e-endpoints)
- [Banco de Dados](#banco-de-dados)
- [Docker](#docker)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Testes](#testes)
- [Contribuindo](#contribuindo)
- [Licença](#licença)
- [Aviso Legal](#aviso-legal)

---

## 📖 Sobre o Projeto

Este projeto é um **Price Monitor Bot** desenvolvido em Java com Spring Boot que permite aos usuários monitorar preços de produtos em websites (com foco em plataformas como Amazon) através do Telegram. 

A aplicação:
- ✅ Faz scraping automático de preços usando JSoup
- ✅ Armazena histórico de preços em PostgreSQL
- ✅ Executa verificações periódicas usando Scheduler
- ✅ Envia notificações em tempo real via Telegram
- ✅ Mantém registro de produtos monitorados por usuário

---

## 🎯 Funcionalidades

### 1. **Web Scraping com JSoup**
- Extrai nome do produto da página
- Captura preço atual em tempo real
- Suporta parsing de elementos HTML específicos
- Tratamento robusto de exceções

### 2. **Agendamento com @Scheduled**
- Verifica preços a cada **1 hora** (3.600.000 ms)
- Execução automática sem intervenção manual
- Integração perfeita com Spring

### 3. **Bot do Telegram**
- Adiciona produtos via envio de URL
- Recebe alertas de redução de preço
- Interface amigável com emojis
- Mensagens formatadas e informativas

### 4. **Banco de Dados**
- Armazena dados de produtos monitorados
- Rastreia histórico de preços
- Registra última verificação
- Status de monitoramento (ativo/inativo)

### 5. **Persistência de Dados**
- PostgreSQL para armazenamento confiável
- Migrations automáticas com Hibernate
- Suporte a JPA/Spring Data

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                    Telegram Bot                              │
│              (TelegramBotPrice Component)                    │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       │ Mensagens do usuário
                       │
┌──────────────────────▼──────────────────────────────────────┐
│              Service Layer                                   │
│  ┌──────────────────────────────────────────────────────┐  │
│  │ ScrapperService                                      │  │
│  │ - buscarNomeProduto(url)                            │  │
│  │ - buscarPreco(url)                                  │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │ ProdutoMonitoradoService                            │  │
│  │ - criarProduto()                                    │  │
│  │ - verifPrice()                                      │  │
│  └──────────────────────────────────────────────────────┘  │
└──────────────────────┬──────────────────────────────────────┘
                       │
        ┌──────────────┼──────────────┐
        │              │              │
        │              │              │
┌───────▼───┐  ┌───────▼────┐  ┌────▼────────┐
│  JSoup    │  │  Scheduler │  │  Database   │
│ (HTML     │  │  (1 hora)  │  │ (PostgreSQL)│
│ Parsing)  │  │            │  │             │
└───────────┘  └───────────┘  └─────────────┘
```

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Java** | 21 | Linguagem principal |
| **Spring Boot** | 4.0.5 | Framework web |
| **Spring Data JPA** | Latest | ORM e persistência |
| **JSoup** | 1.17.2 | 🕷️ Web scraping HTML |
| **PostgreSQL** | 16 | Banco de dados relacional |
| **Telegram Bot API** | 6.9.7.1 | Integração com Telegram |
| **Lombok** | Latest | Redução de boilerplate |
| **Maven** | 3.6+ | Gerenciador de dependências |
| **Docker** | Latest | Containerização |
| **Docker Compose** | Latest | Orquestração de containers |

---

## ⚙️ Pré-requisitos

- **Java 21** ou superior
- **Maven 3.6+** (ou usar `mvnw` fornecido)
- **Docker** e **Docker Compose** (para execução containerizada)
- **PostgreSQL** (pode ser via Docker)
- **Telegram Bot Token** (obtido do BotFather)
- **Git** para clonar o repositório

---

## 🚀 Instalação e Setup

### 1. Clone o Repositório

```bash
git clone https://github.com/victor-macalin/web_scraping.git
cd web_scraping
```

### 2. Configure as Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
# PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/web_scraping_db
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
POSTGRES_USER=postgres
POSTGRES_PASSWORD=sua_senha
POSTGRES_DB=web_scraping_db

# Telegram Bot
BOT_TOKEN=seu_bot_token_aqui
BOT_USERNAME=seu_bot_username_aqui
```

### 3. Instale as Dependências

```bash
# Linux/Mac
./mvnw clean install

# Windows
mvnw.cmd clean install

# Ou com Maven instalado globalmente
mvn clean install
```

### 4. Execute a Aplicação

#### Opção A: Com Maven

```bash
./mvnw spring-boot:run
```

#### Opção B: Com Docker Compose (Recomendado)

```bash
docker-compose up --build
```

#### Opção C: JAR Executável

```bash
# Build
./mvnw clean package

# Executar
java -jar target/web_scrapping-0.0.1-SNAPSHOT.jar
```

---

## 🔧 Configuração

### Application.yml

O arquivo `src/main/resources/application.yml` contém as configurações da aplicação:

```yaml
spring:
  application:
    name: web_scrapping
  
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  
  jpa:
    hibernate:
      ddl-auto: update  # Auto-criação de tabelas
    show-sql: true     # Log de SQL
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

telegram:
  bot:
    token: ${BOT_TOKEN}
    username: ${BOT_USERNAME}
```

### Obtaining Telegram Bot Token

1. Abra o Telegram e procure por **@BotFather**
2. Digite `/newbot` e siga as instruções
3. Você receberá um token: `123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11`
4. Guarde este token com segurança

---

## 💬 Como Usar

### Via Telegram Bot

1. **Encontre o bot**: Procure pelo nome do seu bot no Telegram

2. **Adicione um produto**: Envie uma URL de produto
   ```
   https://www.amazon.com.br/seu-produto/dp/XXXXXXXXX
   ```

3. **Confirmação**: Você receberá uma mensagem com:
   - ✅ Nome do produto
   - 💰 Preço atual
   - 🔍 Confirmação de monitoramento

4. **Receba Alertas**: Quando o preço cair, você receberá:
   ```
   📉 Boa notícia! O preço do produto que você está monitorando baixou!
   
   🛍️ Produto: Nome do Produto
   💸 Preço anterior: R$ 100.00
   🔥 Novo preço: R$ 85.00
   🚀 Aproveite essa oportunidade antes que o valor mude novamente!
   
   🔗 Link do produto:
   https://www.amazon.com.br/...
   ```

### Fluxo de Funcionamento

```
Usuário envia URL
       ↓
ScrapperService busca dados
       ↓
ProdutoMonitorado salvo no DB
       ↓
Bot confirma no Telegram
       ↓
Scheduler verifica a cada 1 hora
       ↓
Se preço caiu → Envia alerta
```

---

## 📁 Estrutura do Projeto

```
web_scraping/
├── src/
│   ├── main/
│   │   ├── java/com/dev/victor/web_scrapping/
│   │   │   ├── WebScrappingApplication.java      # Main class com @EnableScheduling
│   │   │   ├── model/
│   │   │   │   └── ProdutoMonitorado.java        # Entity JPA
│   │   │   ├── service/
│   │   │   │   ├── ScrapperService.java          # 🕷️ JSoup scraping
│   │   │   │   └── ProdutoMonitoradoService.java # Lógica de negócio
│   │   │   ├── repository/
│   │   │   │   └── ProdutoMonitoradoRepository.java  # Spring Data JPA
│   │   │   ├── scheduler/
│   │   │   │   └── SchedulerMontoramento.java    # ⏰ @Scheduled tasks
│   │   │   ├── telegram/
│   │   │   │   ├── TelegramBotPrice.java         # 🤖 Bot principal
│   │   │   │   └── TelegramConfig.java           # Configurações
│   │   │   └── config/
│   │   │       └── TelegramConfig.java
│   │   └── resources/
│   │       └── application.yml                   # Configurações
│   └── test/
│       └── WebScrappingApplicationTests.java
├── .mvn/wrapper/                                 # Maven Wrapper
├── docker-compose.yml                            # Orquestração de containers
├── pom.xml                                       # Dependências Maven
├── mvnw                                          # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                                      # Maven Wrapper (Windows)
└── README.md                                     # Este arquivo
```

---

## 🔌 Classes Principais

### 1. **ScrapperService.java** 🕷️
Responsável pelo web scraping com JSoup:

```java
@Service
public class ScrapperService {
    // Busca nome do produto via seletor CSS #productTitle
    public String buscarNomeProduto(String url) throws IOException
    
    // Busca preço via seletor CSS .a-offscreen
    public BigDecimal buscarPreco(String url) throws IOException
}
```

**Seletores CSS Utilizados:**
- `#productTitle` - Título do produto (Amazon)
- `.a-offscreen` - Preço do produto (Amazon)

### 2. **SchedulerMontoramento.java** ⏰
Executa verificação periódica de preços:

```java
@Component
public class SchedulerMontoramento {
    // Executa a cada 1 hora (3.600.000 ms)
    @Scheduled(fixedRate = 3600000)
    public void verifPrice()
}
```

**Como Funciona:**
1. Busca todos os produtos ativos
2. Verifica preço atual para cada um
3. Compara com preço anterior
4. Envia alerta se houver redução

### 3. **TelegramBotPrice.java** 🤖
Bot do Telegram que gerencia interações:

```java
@Component
public class TelegramBotPrice extends TelegramLongPollingBot {
    // Recebe mensagens do usuário
    public void onUpdateReceived(Update update)
    
    // Envia alertas de redução de preço
    public void sendAlert(ProdutoMonitorado produto)
}
```

### 4. **ProdutoMonitorado.java** 📦
Entidade JPA que representa um produto:

```java
@Entity
@Table(name = "produtos")
public class ProdutoMonitorado {
    Long id;
    Long chatId;           // ID da conversa no Telegram
    String url;            // URL do produto
    String nomeProduto;    // Nome extraído
    BigDecimal precoAtual; // Preço atual
    BigDecimal precoAnterior; // Preço anterior
    LocalDateTime dataCadastro;
    LocalDateTime ultimaVerificacao;
    Boolean ativo;         // Monitoramento ativo?
}
```

---

## 🗄️ Banco de Dados

### Tabela: `produtos`

```sql
CREATE TABLE produtos (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    url VARCHAR(1000) NOT NULL,
    nome_produto VARCHAR(1000),
    preco_atual NUMERIC(10,2),
    preco_anterior NUMERIC(10,2),
    data_cadastro TIMESTAMP,
    ultima_verificacao TIMESTAMP,
    ativo BOOLEAN
);
```

### Diagrama Entidade-Relacionamento

```
┌─────────────────────────────┐
│     PRODUTOS                │
├─────────────────────────────┤
│ id (PK)                     │
│ chat_id (FK - Telegram)     │
│ url                         │
│ nome_produto                │
│ preco_atual                 │
│ preco_anterior              │
│ data_cadastro               │
│ ultima_verificacao          │
│ ativo                       │
└─────────────────────────────┘
```

---

## 🐳 Docker

### Executar com Docker Compose

```bash
docker-compose up -d
```

Isso iniciará:
- **PostgreSQL 16** na porta 5432
- **Web Scraping App** na porta 8080 (se configurado)

### Docker Compose File

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:16
    container_name: produtomonitoradodb
    restart: always
    environment:
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      POSTGRES_DB: ${POSTGRES_DB}
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

### Criar Dockerfile (se necessário)

```dockerfile
FROM maven:3.8.6-openjdk-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🔑 Variáveis de Ambiente

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `DB_URL` | URL do PostgreSQL | `jdbc:postgresql://localhost:5432/web_scraping_db` |
| `DB_USERNAME` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `sua_senha_segura` |
| `POSTGRES_USER` | Usuário Postgres | `postgres` |
| `POSTGRES_PASSWORD` | Senha Postgres | `sua_senha_segura` |
| `POSTGRES_DB` | Nome do banco | `web_scraping_db` |
| `BOT_TOKEN` | Token do Telegram | `123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11` |
| `BOT_USERNAME` | Username do bot | `seu_bot_aqui` |

### Arquivo .env Exemplo

```env
# Database Configuration
DB_URL=jdbc:postgresql://postgres:5432/web_scraping_db
DB_USERNAME=postgres
DB_PASSWORD=postgres123

# Docker Postgres
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres123
POSTGRES_DB=web_scraping_db

# Telegram Bot
BOT_TOKEN=123456789:ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgh
BOT_USERNAME=seu_bot_scraper
```

---

## 🧪 Testes

### Executar Testes

```bash
./mvnw test
```

### Dependências de Teste

- JUnit (via Spring Boot Test)
- Mockito
- AssertJ

### Exemplo de Teste

```java
@SpringBootTest
class WebScrappingApplicationTests {
    
    @Test
    void contextLoads() {
        // Teste de carregamento do contexto
    }
}
```

---

## 🔗 API e Endpoints

### Endpoints Disponíveis

A aplicação atualmente funciona principalmente via **Telegram Bot**. Para adicionar endpoints REST, você pode criar controllers como:

```java
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    @GetMapping
    public List<ProdutoMonitorado> listarProdutos() { }
    
    @PostMapping
    public ProdutoMonitorado criarProduto(@RequestBody ProdutoMonitorado produto) { }
    
    @GetMapping("/{id}")
    public ProdutoMonitorado obterProduto(@PathVariable Long id) { }
}
```

---

## 📊 Fluxo de Dados

```
1. Usuário → Telegram Bot
   └─> Envia URL do produto

2. TelegramBotPrice.onUpdateReceived()
   └─> Valida se é URL

3. ScrapperService
   ├─> Busca nome via JSoup
   └─> Busca preço via JSoup

4. ProdutoMonitoradoService.criarProduto()
   └─> Salva no PostgreSQL

5. TelegramBotPrice.execute()
   └─> Confirma no Telegram

6. SchedulerMontoramento (@Scheduled 1h)
   ├─> Busca produtos ativos
   ├─> Verifica preço atual
   ├─> Compara com anterior
   └─> Se caiu → sendAlert()

7. TelegramBotPrice.sendAlert()
   └─> Notifica usuário no Telegram
```

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. **Fork o repositório**
   ```bash
   git clone https://github.com/seu-usuario/web_scraping.git
   ```

2. **Crie uma branch para sua feature**
   ```bash
   git checkout -b feature/MinhaNovaFuncionalidade
   ```

3. **Faça commit de suas mudanças**
   ```bash
   git commit -m 'Adiciona MinhaNovaFuncionalidade'
   ```

4. **Push para a branch**
   ```bash
   git push origin feature/MinhaNovaFuncionalidade
   ```

5. **Abra um Pull Request**

### Diretrizes de Contribuição

- Mantenha o código limpo e bem documentado
- Adicione testes para novas funcionalidades
- Siga as convenções de nomenclatura Java
- Atualize o README se necessário

---

## 📝 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

```
MIT License

Copyright (c) 2024 Victor Macalin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## ⚠️ Aviso Legal

### Responsabilidade de Uso

Este projeto é fornecido **apenas para fins educacionais**. O usuário é responsável por:

1. **Respeitar `robots.txt`**: Sempre verifique o arquivo `robots.txt` do website
   ```
   https://www.amazon.com.br/robots.txt
   ```

2. **Cumprir Termos de Serviço**: Respeite os TOS de cada plataforma

3. **Rate Limiting**: Não faça requisições excessivas
   ```java
   // O projeto usa 1 requisição a cada 1 hora
   @Scheduled(fixedRate = 3600000) // 1 hora
   ```

4. **User-Agent**: O projeto identifica-se como navegador
   ```java
   Jsoup.connect(url).userAgent("Mozilla/5.0")
   ```

### Limitações

- ⚠️ Alguns websites bloqueiam scrapers
- ⚠️ Pode ser necessário ajustar seletores CSS
- ⚠️ Não funciona com JavaScript (considere Selenium se necessário)

### Aviso de Responsabilidade

O desenvolvedor **não é responsável** por:
- Bloqueio de IPs por websites
- Uso indevido da ferramenta
- Violação de termos de serviço
- Danos causados pela aplicação

---

## 📞 Suporte e Contato

### Reportar Issues

Para reportar bugs ou sugerir funcionalidades:

1. Abra uma [Issue no GitHub](https://github.com/victor-macalin/web_scraping/issues)
2. Descreva o problema detalhadamente
3. Inclua logs e passos para reproduzir

### Exemplo de Issue Bem Formatada

```markdown
## Título: Bot não envia alerta quando preço cai

### Descrição
Quando configuro um produto para monitoramento, o bot confirma mas não envia alerta quando o preço reduz.

### Passos para Reproduzir
1. Envie uma URL para o bot
2. Aguarde 1 hora
3. Preço reduz mas nenhum alerta é enviado

### Logs
[Incluir logs do console]

### Ambiente
- Java: 21
- Spring Boot: 4.0.5
- SO: Linux
```

---

## 🌟 Status do Projeto

- ✅ Web Scraping com JSoup
- ✅ Scheduler (1 hora)
- ✅ Telegram Bot
- ✅ Banco de Dados PostgreSQL
- 🔄 Em desenvolvimento
- 📋 Features planejadas:
  - [ ] REST API completa
  - [ ] Frontend web
  - [ ] Gráficos de histórico de preços
  - [ ] Suporte a múltiplos websites
  - [ ] Integração com Discord
  - [ ] Autenticação de usuários

---

## 📚 Recursos Úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JSoup Documentation](https://jsoup.org/)
- [Telegram Bot API](https://core.telegram.org/bots/api)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

---

## 🙏 Agradecimentos

- Spring Framework por excelente framework
- JSoup por parsing HTML
- Telegram por API robusta
- PostgreSQL por banco confiável

---

<div align="center">

### Feito com ❤️ por Victor Macalin

⭐ Se este projeto foi útil, considere deixar uma star! ⭐

[GitHub](https://github.com/victor-macalin) | [Telegram](https://t.me/victor_macalin)

</div>

---

## 🔄 Changelog

### v0.0.1 (Atual)
- Versão inicial do projeto
- Web Scraping com JSoup
- Scheduler de verificação
- Bot do Telegram
- Banco de dados PostgreSQL

---

**Última atualização**: Abril 2024
