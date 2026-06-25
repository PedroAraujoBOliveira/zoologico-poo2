# Sistema de Gestao de Zoologico - POO2

Aplicacao desktop em Java para cadastrar, consultar, editar e remover animais, especies e registros de alimentacao de um zoologico. O projeto usa heranca, polimorfismo, tratamento de excecoes, JDBC, MySQL e interface grafica Swing.


👨‍💻 𝓓𝓮𝓼𝓮𝓷𝓿𝓸𝓵𝓿𝓭𝓸𝓻𝓮𝓼 𝓭𝓸 𝓟𝓻𝓸𝓳e𝓽𝓸

| ID | Equipe |
| :--: | :-----------------------------------------------------------------------: | 
| 01 | Lucas Gabriel
| 02 | Luhan Christyan Rodrigues
| 03 | Pedro Araujo 

## Requisitos atendidos

- Cadastro de especies e associacao de cada animal a uma especie.
- Cadastro de aves, mamiferos e repteis com caracteristica especifica.
- Listagem e busca de animais por nome ou especie.
- Edicao e exclusao de animais.
- Registro de alimentacao para um animal selecionado.
- Consulta do historico de alimentacao por animal.
- Persistencia em MySQL e validacoes de regras de negocio.

Autenticacao de usuarios nao faz parte desta versao, conforme o escopo definido para POO2.

## Requisitos locais

- JDK 21.
- MySQL Server 8 ou superior em execucao.
- Maven 3.9 ou superior. O projeto tambem possui Maven Wrapper (`mvnw.cmd`) para facilitar a execucao no Windows.

## Preparar o banco

No MySQL Workbench, abra e execute o arquivo `database/schema.sql`. Ele cria o banco `zoologico_poo2` e as tabelas necessarias.

Para carregar dados prontos para demonstracao, execute em seguida o arquivo opcional `database/dados-exemplo.sql`.

Defina a senha do usuario `root` somente no terminal integrado do VS Code ou PowerShell:

```powershell
$env:ZOO_DB_PASSWORD = 'SUA_SENHA_DO_MYSQL'
```

Se voce alterou a porta, usuario ou nome do banco, ajuste `src/main/resources/database.properties`.

## Executar

No terminal aberto na pasta do projeto:

```powershell
.\mvnw.cmd test
.\mvnw.cmd exec:java
```

Caso o Maven Wrapper nao seja utilizado, execute `mvn test` e `mvn exec:java`.

Na primeira execucao, a aplicacao tambem verifica e cria as tabelas `especies`, `animais` e `alimentacoes` quando necessario.

## Organizacao

- `model`: entidades e hierarquia de animais.
- `dao`: persistencia JDBC.
- `controller`: intermediacao entre as telas e as regras de negocio (MVC).
- `service`: regras de negocio e validacoes.
- `exception`: excecoes da aplicacao.
- `view`: telas Swing.
- `infra`: conexao e criacao da estrutura do banco.
