# API REST de consertos

Implementação-base da Avaliação 3, reunindo as etapas dos Conteúdos 07, 08 e 09. Os arquivos Java ficam diretamente em `src/main/java` e usam o pacote padrão, conforme solicitado.

## Requisitos

- Java 17+
- Maven
- Spring Boot 4.0.0, Spring Data JPA, Validation e Flyway
- H2 em arquivo, Lombok e migrations SQL

## Executar

Na pasta deste projeto:

```sh
mvn spring-boot:run
```

O H2 Console fica em `http://localhost:8080/h2-console`. JDBC URL: `jdbc:h2:file:./DATA/consertos`; usuário `sa`; senha vazia. As migrations criam e evoluem a tabela `consertos`.

## Endpoints

- `POST /consertos`: cria e responde `201 Created`, com `Location` e o recurso criado.
- `GET /consertos`: listagem completa paginada (parâmetros `page` e `size`).
- `GET /consertos/algunsdados`: listagem paginada resumida, com ID e apenas registros ativos.
- `GET /consertos/{id}`: consulta de um registro ativo; `404` se não existir ou estiver inativo.
- `PUT /consertos/{id}`: altera apenas `dataSaida`, `nomeMecanico` e `anosExperienciaMecanico`; campos omitidos ou nulos não são alterados.
- `DELETE /consertos/{id}`: marca `ativo = false` e responde `204 No Content`; não apaga a linha.

Exemplo de cadastro:

```json
{
  "dataEntrada": "07/10/2026",
  "dataSaida": null,
  "mecanico": { "nome": "Ana Souza", "anosExperiencia": 8 },
  "veiculo": { "marca": "Fiat", "modelo": "Uno", "ano": "2018", "cor": "Prata" }
}
```

Exemplo de atualização:

```json
{
  "dataSaida": "08/10/2026",
  "nomeMecanico": "Ana Souza",
  "anosExperienciaMecanico": 9
}
```

A aplicação valida formato `dd/MM/yyyy` para datas quando informadas, exige nome do mecânico, marca, modelo e ano com quatro dígitos. A cor é opcional. Se o projeto original já tiver migrations ou nomes de coluna diferentes, adapte os arquivos `V1` e `V2` à sua sequência/esquema; `V3` representa a migration nova de `ativo` pedida na parte 3.
