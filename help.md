# Instruções de Uso da Aplicação

## Requisitos
- Java 17+
- PostgreSQL 14+
- Maven 3.8+

## Configuração do Banco de Dados
1. Crie um banco de dados PostgreSql com o nome `petshop`:
2. Configure o banco no `application.properties` ou `application.yml`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/petshop
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    spring.jpa.hibernate.ddl-auto=create-drop
    ```
3. Para popular os dados, use o script `data.sql` no diretório `resources`.

## Como Executar

1. Certifique-se de que o PostgreSQL está rodando.
2. Compile e execute a aplicação:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

## Endpoints Principais

### Documentação
- `http://localhost:8080/swagger-ui/index.html` - Swagger

### Metricas
- `http://localhost:8080/actuator` - Actuator


### Autenticação
- `POST /api/auth/login` - Autentica um usuário

- `POST /api/auth/register` - Registra um novo usuário

### Clientes
- `POST /api/clientes` - Cria um cliente

- `GET /api/clientes/{cpf}` - Obtém dados do cliente

### Pets
- `GET /api/pets/{id}` - Obtém informações de um pet

- `POST /api/pets` - Cadastra um novo pet

- `DELETE /api/pets/{id}` - Remove um pet

### Fotos
- `POST /api/fotos/cliente/{cpf}` - Adiciona uma foto para cliente

- `GET /api/fotos/cliente/{cpf}` - Obtém fotos do cliente

- `POST /api/fotos/pet/{idPet}` - Adiciona foto do pet

- `GET /api/fotos/pet/{idPet}` - Obtém fotos do pet

### Raças
- `GET /api/racas` - Listar raças

- `POST /api/racas` - Criar raça

- `PUT /api/racas/{id}` - Atualizar raça

### Endereços
- `PUT /api/enderecos/{id}` - Atualizar endereço

- `DELETE /api/enderecos/{id}` - Deletar endereço

### Contatos
- `PUT /api/contatos/{id}` - Atualizar contato

- `DELETE /api/contatos/{id}` - Deletar contato

### Usuários
- `GET /api/usuarios` - Lista todos usuários

- `POST /api/usuarios` - Criar usuário

- `GET /api/usuarios/{cpf}` - Listar usuário por CPF

- `DELETE /api/usuarios/{cpf}` - Deletar usuário

### Atendimentos
- `POST /api/atendimentos` - Gerar atendimento

## Testes
Para rodar os testes:
```sh
mvn test
```

## Contato
Para suporte, entre em contato pimentadesenvolvimento@gmail.com.

