# API REST de consertos

API para registrar e acompanhar consertos de veículos. A aplicação usa Java 21, Spring Boot 4, Spring MVC, Spring Data JPA, Bean Validation, Spring Security, JWT, Flyway e banco H2 em arquivo.

## Requisitos

- JDK 21
- Maven 3.6 ou superior

## Configuração e execução

Defina `JWT_SECRET` com um segredo forte antes de iniciar. Se omitido, a aplicação usa `12345678`, valor adequado apenas para desenvolvimento local.

```powershell
$env:JWT_SECRET = "substitua-por-um-segredo-forte"
mvn spring-boot:run
```

A API fica em `http://localhost:8080`. O banco H2 persiste em `./DATA/consertos`; o console fica em `http://localhost:8080/h2-console`, com JDBC URL `jdbc:h2:file:./DATA/consertos`, usuário `sa` e senha vazia. O console pode exigir autenticação conforme a configuração de segurança da aplicação.

As migrations Flyway em `src/main/resources/db/migration` criam as tabelas de consertos e usuários e aplicam as alterações de esquema.

## Autenticação

As rotas, exceto `POST /login`, exigem JWT no cabeçalho:

```http
Authorization: Bearer <token>
```

Envie as credenciais cadastradas para `POST /login`. A migration inicial cria o usuário `asdrubal@gmail.com`, com senha `123456`.

```json
{
  "login": "asdrubal@gmail.com",
  "senha": "123456"
}
```

A resposta contém o token:

```json
{
  "token": "<token-jwt>"
}
```

## Endpoints

| Método | Rota | Descrição |
| --- | --- | --- |
| `POST` | `/login` | Autentica e retorna um JWT. |
| `POST` | `/consertos` | Cadastra conserto e retorna `201 Created`, `Location` e os dados criados. |
| `GET` | `/consertos` | Lista todos os registros em páginas (`page`, `size`), incluindo inativos. |
| `GET` | `/consertos/algunsdados` | Lista apenas registros ativos com dados resumidos, em páginas. |
| `GET` | `/consertos/{id}` | Consulta registro ativo; retorna `404` se ausente ou inativo. |
| `PUT` | `/consertos/{id}` | Atualiza os campos informados entre data de saída, nome e experiência do mecânico. |
| `DELETE` | `/consertos/{id}` | Desativa o registro e retorna `204 No Content`; retorna `404` se ausente ou inativo. |

As rotas de consertos exigem token. As listagens aceitam paginação padrão do Spring, como `?page=0&size=10`.

### Cadastro

```json
{
  "dataEntrada": "07/10/2026",
  "dataSaida": null,
  "mecanico": { "nome": "Ana Souza", "anosExperiencia": 8 },
  "veiculo": { "marca": "Fiat", "modelo": "Uno", "ano": "2018", "cor": "Prata" }
}
```

### Atualização parcial

```json
{
  "dataSaida": "08/10/2026",
  "nomeMecanico": "Ana Souza",
  "anosExperienciaMecanico": 9
}
```

Datas, quando informadas, devem seguir `dd/MM/yyyy`; o ano do veículo deve conter quatro dígitos. Nome do mecânico, marca, modelo e data de entrada são obrigatórios. A cor e a data de saída são opcionais. Erros de validação retornam `400`; credenciais inválidas retornam `401`.

## Testes

```sh
mvn test
```

Os testes unitários de `Conserto` cobrem criação, atualização parcial e exclusão lógica.
