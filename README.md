# Coding Challenge

API REST criada no Coding Challenge | Java do bootcamp Itáu {devs} Powered by LET'S CODE.

De acordo com o perfil do usuário é possível se cadastrar, buscar por filmes, dar nota e realizar comentários.

## Pré-requisitos

| Dependency                                                                                 | Version         |
| ---------------------------------                                                          | --------------- |
| [Java](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)    | 11.x  or later  |
| [PostgreSQL](https://www.postgresql.org/download/)                                         | 		       |

## Configurar Banco de Dados

Em src/main/resources/application.properties é preciso preencher os valores para congiurar o banco de dados na aplicação 
```bash
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```
Em src/main/java/com.api.codingchallenge/config/SeederConfig.java tem o arquivo de configurações que faz o cadastro de alguns registros inicias para ficilitar testes.

No arquivo application.properties a opção dll-auto está com o valor create (com isso o banco de dados sempre vai ser recriado). 
Caso opte por trocar o valor do dll-auto para update remover arquivo SeederConfig.java

## Configurar JWT

Ainda no arquivo application.properties temos a configuração para geração do token
```bash
security.config.prefix=
security.config.key=
security.config.expiration=
```

## Configurar chave da Api externa OMDb

Por fim no arquivo application.properties adicionar a chave privada para ter acesso aos filmes
```bash
omdbapi-key=
```

## Funcionalidades

A API com os endpoints está documentado no Postman na seguinte url https://documenter.getpostman.com/view/21734610/UzJETKZh

Caso opte por "Run in Postman" 

* mudar o ambiente para Development
* definir váriavel global "local" com o caminho que a aplicação está rodando
* criar uma variável token (após o login o token é definido nessa variável passada no Authorization Header das requests)
