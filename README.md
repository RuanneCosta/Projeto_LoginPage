# Projeto Login Page — Spring Boot + MySQL

Sistema web de autenticação de usuários, desenvolvido como projeto de estudo em Java com Spring Boot. A aplicação permite cadastro de novos usuários, login com validação de credenciais, controle de sessão via cookies e uma tela de painel (dashboard) protegida, acessível apenas para usuários autenticados.

## Funcionalidades

- Cadastro de novos usuários, com os dados persistidos em um banco de dados MySQL
- Login com validação de e-mail e senha
- Exibição de mensagem de erro na tela de login quando as credenciais são inválidas
- Autenticação de sessão baseada em cookies (sem uso de bibliotecas de segurança prontas, implementada manualmente para fins de aprendizado)
- Bloqueio de acesso a rotas protegidas (como o dashboard) para usuários não autenticados, usando um interceptor HTTP
- Painel (dashboard) exibido após o login bem-sucedido (ilustrativo)

## Tecnologias e dependências

- **Java** com **Spring Boot**
- **Spring MVC** — controllers e mapeamento de rotas (`@Controller`, `@GetMapping`, `@PostMapping`)
- **Spring Data JPA** — camada de persistência, com `UsuarioRepository`
- **Hibernate** — ORM responsável por mapear a entidade `Usuario` para a tabela do banco e gerar o schema automaticamente
- **MySQL** — banco de dados relacional
- **Thymeleaf** — motor de templates para renderização das páginas HTML no servidor
- **HTML e CSS** — construção das interfaces de login, cadastro e dashboard
- **Bean Validation (`jakarta.validation`)** — validação de campos obrigatórios na entidade `Usuario` (`@NotEmpty`)

## Conceitos e práticas aplicadas

Durante o desenvolvimento, este projeto passou por diversos ajustes que reforçaram conceitos importantes de Spring Boot e boas práticas de desenvolvimento:

- **Component scan e organização de pacotes**: todas as classes (`controller`, `model`, `repository`, `services`) foram organizadas dentro do pacote raiz da aplicação, respeitando o escaneamento automático de componentes do Spring Boot.
- **Configuração manual de `DataSource` e `JpaVendorAdapter`**: entendimento de como o Spring Boot configura a conexão com o banco de dados e o dialeto do Hibernate.
- **Redirecionamento entre rotas (`redirect:`)**: uso correto do prefixo `redirect:` para navegação entre páginas após ações como cadastro e login.
- **Model e Thymeleaf**: uso de `Model` para passar atributos do Controller para a View, e `th:if` / `th:text` para renderização condicional de mensagens de erro.
- **Manipulação de cookies em Java puro**: criação de uma classe utilitária (`CookieServer`) para salvar e ler cookies HTTP, incluindo codificação/decodificação de valores (`URLEncoder` / `URLDecoder`).
- **Interceptors HTTP (`HandlerInterceptor`)**: implementação de um interceptor (`LoginInterceptor`) para verificar a autenticação do usuário antes de liberar o acesso a rotas protegidas, registrado via `WebMvcConfigurer`.
- **Boas práticas de segurança**: remoção de credenciais do banco de dados do código-fonte, substituídas por variáveis de ambiente; criação de um usuário de banco de dados específico para o projeto, com permissões restritas apenas ao seu próprio schema (em vez de usar o usuário `root`).
- **Prevenção de dados duplicados**: identificação e correção de registros duplicados no banco, com discussão sobre o uso de restrições de unicidade (`@Column(unique = true)`) para evitar o problema na origem.


## Como executar o projeto localmente

### Pré-requisitos

- Java (JDK compatível com a versão do projeto)
- Maven
- MySQL em execução localmente

### Configuração do banco de dados

Crie o banco de dados e um usuário dedicado ao projeto:

```sql
CREATE DATABASE applogin;

CREATE USER 'applogin_user'@'localhost' IDENTIFIED BY 'sua-senha-aqui';
GRANT ALL PRIVILEGES ON applogin.* TO 'applogin_user'@'localhost';
FLUSH PRIVILEGES;
```

### Variáveis de ambiente

A aplicação lê as credenciais do banco de dados a partir de variáveis de ambiente, com valores padrão de fallback definidos em `application.properties`. Configure as seguintes variáveis antes de rodar o projeto:

| Variável | Descrição |
|---|---|
| `DB_USER` | Usuário do banco de dados MySQL |
| `DB_PASSWORD` | Senha do usuário do banco de dados |


A aplicação estará disponível em `http://localhost:8080/login`.

## Próximos passos (ideias de evolução)

- Criptografar as senhas dos usuários antes de salvar no banco (ex.: `BCryptPasswordEncoder`)
- Migrar a autenticação manual via cookies para o Spring Security
- Adicionar validação de confirmação de senha no cadastro
- Popular o dashboard com dados reais vindos do banco, em vez de dados ilustrativos
- Adicionar testes automatizados

## Créditos

Este projeto foi desenvolvido com base no tutorial em vídeo de **[Nome do Criador]**, 
disponível em seu canal do YouTube: Tácio | Dev para Iniciantes(https://www.youtube.com/@devparainiciante).

O código foi implementado, depurado e adaptado por mim durante o processo de aprendizado, 
incluindo ajustes de estrutura de pacotes, tratamento de erros, autenticação via cookies e melhorias de segurança (remoção de credenciais do código-fonte).
