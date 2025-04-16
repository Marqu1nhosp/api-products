# REST API - Spring Products v1

API para gestão de produtos com operações de CRUD para categorias, produtos e usuários. Esta API foi desenvolvida utilizando o Spring Framework.

## Documentação da API

A especificação da API segue o padrão OpenAPI 3.1.0, e a documentação pode ser acessada em:

- **URL base**: `http://localhost:8080`
- **Documentação da API**: `/docs-product`

## Licença

Esta API está licenciada sob a Licença Apache 2.0.

## Autenticação

Recurso para autenticação na API.

- **POST** `/api/v1/auth` - Autenticar na API.

  **Exemplo de autenticação:**

  - **URL**: `/api/v1/auth`
  - **Método**: POST
  - **Body**:
    ```json
    {
      "username": "seu-usuario",
      "password": "sua-senha"
    }
    ```

## Recursos da API

### Categorias

Contém todas as operações relativas ao recurso para cadastro, edição, exclusão e leitura de categorias.

- **GET** `/api/v1/categories/{id}` - Buscar uma categoria.
- **PUT** `/api/v1/categories/{id}` - Alterar uma categoria.
- **DELETE** `/api/v1/categories/{id}` - Deletar uma categoria.
- **GET** `/api/v1/categories` - Listar todas as categorias.
- **POST** `/api/v1/categories` - Criar uma nova categoria.

### Usuários

Contém todas as operações relativas ao recurso para cadastro, edição, exclusão e leitura de usuários.

- **GET** `/api/v1/users` - Listar todos os usuários.
- **POST** `/api/v1/users` - Criar um novo usuário.
- **GET** `/api/v1/users/{id}` - Recuperar um usuário pelo ID.
- **PATCH** `/api/v1/users/{id}` - Atualizar senha do usuário.

### Produtos

Contém todas as operações relativas ao recurso para cadastro, edição, exclusão e leitura de produtos.

- **GET** `/api/v1/products/{id}` - Buscar um produto.
- **PUT** `/api/v1/products/{id}` - Alterar um produto.
- **DELETE** `/api/v1/products/{id}` - Deletar um produto.
- **GET** `/api/v1/products` - Listar todos os produtos.
- **POST** `/api/v1/products` - Criar um novo produto.

## Schemas

Abaixo estão os esquemas de dados utilizados para as operações na API:

### ProductCreateDto

- **name** (string): Nome do produto.
- **description** (string): Descrição do produto.
- **price** (float): Preço do produto.
- **categoryId** (integer): ID da categoria do produto.

### ProductUpdateDto

- **name** (string): Nome do produto.
- **description** (string): Descrição do produto.
- **price** (float): Preço do produto.
- **categoryId** (integer): ID da categoria do produto.

### CategoryCreateDto

- **name** (string): Nome da categoria.

### CategoryUpdateDto

- **name** (string): Nome da categoria.

### UserCreateDto

- **username** (string): Nome de usuário.
- **password** (string): Senha do usuário.

### UserLoginDto

- **username** (string): Nome de usuário.
- **password** (string): Senha do usuário.

### UserUpdatePasswordDto

- **currentPassword** (string): Senha atual.
- **newPassword** (string): Nova senha.
- **confirmPassword** (string): Confirmação da nova senha.

### UserResponseDto

- **id** (integer): ID do usuário.
- **username** (string): Nome de usuário.

---

## Como Rodar a API

### Pré-requisitos

- Java 11 ou superior
- Maven
- Banco de dados configurado (MySQL)

### Passos para executar:

1. Clone este repositório:
   ```bash
   git clone https://github.com/marqu1nhosp/api-products.git
