# api-movie - Sistema de Gerenciamento de Filmes

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

api-movie é uma aplicação backend monolítica desenvolvida para gerenciar dados relacionados a filmes, proporcionando uma interface completa para operações de CRUD (Create, Read, Update, Delete) em diferentes recursos.

# Funcionalidades Principais
A aplicação oferece quatro APIs principais de modelos APIREST e APIRESTful, cada uma focada em um recurso específico:

APIRESTful para Genre: Gerencia os gêneros de filmes, permitindo criar, visualizar, atualizar e excluir categorias de gênero.

APIRESTful para Movie: Gerencia os dados dos filmes, incluindo informações como título, descrição, e gênero. Permite operações de CRUD para manter o catálogo de filmes atualizado.

APIREST para Review: Permite a criação, leitura, atualização e exclusão de resenhas de filmes, facilitando a interação dos usuários com o conteúdo.

APIRESTful para User: Gerencia os usuários da aplicação, incluindo a capacidade de criar, visualizar, atualizar e excluir perfis de usuário.

# O que a aplicação proporciona?
api-movie foi projetada para oferecer uma solução robusta para o gerenciamento de filmes e suas interações com usuários. Com uma arquitetura monolítica, a aplicação integra de forma coesa todas as funcionalidades necessárias para a administração de gêneros, filmes, resenhas e usuários, ideal para plataformas de streaming, sites de crítica de cinema, ou qualquer aplicação relacionada ao mundo dos filmes.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Technologies Used](#technologies-used)
- [Observation](#observation)
- [Contributing](#contributing)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/IgorTecnologia/api-movie.git
```

2. Install dependencies with Maven

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080

## Collection Postman

Download these files and import them into your Postman to use the ready-made HTTP methods along with the already configured environment variables, to perform the requests/responses

[Download Collections](https://github.com/IgorTecnologia/api-movie/blob/docs-postman/api-movie-collection.json)

[Download Environment variables](https://github.com/IgorTecnologia/api-movie/blob/docs-postman/Local-host-environment.json)

## API Endpoints
The API provides the following endpoints:

**GET MOVIES**
```markdown
GET /users - Retrieve a pagination of all movies.
```
```json
{
    "content": [
        {
            "id": "abffc6bd-8dcc-4f10-8ad0-9c5ec1c13059",
            "title": "O Garfild",
            "subTitle": "O gato sapéca",
            "yearOfRelease": 2012,
            "imgUrl": "www.img/url.com",
            "synopsis": "Um gato para lá de esperto",
            "genre": {
                "id": "f891c878-9f2d-494d-bb9c-76d74824387d",
                "name": "Comédia",
                "movies": [],
                "reviews": [],
                "links": []
            },
            "reviews": [
                {
                    "id": "f02ddf59-7e76-4717-89b3-c4cbb87a8b6b",
                    "text": "Muito engraçado",
                    "user": {
                        "id": "f0a0967b-4d58-45f6-b743-d26b20e3e88f",
                        "name": "Nanci",
                        "email": "nanci@gmail.com",
                        "roles": [],
                        "links": []
                    },
                }
                ],
        }
]
}
```
**GET MOVIES**
```markdown
GET /users/title/{title} - Retrieve a list of movies filtered by title.
Exemple: GET /movies/title/garfild
```
```json
   [
    {
        "id": "abffc6bd-8dcc-4f10-8ad0-9c5ec1c13059",
        "title": "O Garfild",
        "subTitle": "O gato sapéca",
        "yearOfRelease": 2012,
        "imgUrl": "www.img/url.com",
        "synopsis": "Um gato para lá de esperto",
        "genre": {
            "id": "f891c878-9f2d-494d-bb9c-76d74824387d",
            "name": "Comédia",
            "movies": [],
            "reviews": [],
            "links": []
        },
        "reviews": [
            {
                "id": "f02ddf59-7e76-4717-89b3-c4cbb87a8b6b",
                "text": "Muito engraçado",
                "user": {
                    "id": "f0a0967b-4d58-45f6-b743-d26b20e3e88f",
                    "name": "Nanci",
                    "email": "nanci@gmail.com",
                    "roles": [],
                    "links": []
                }
        }
        ]
        }
]

```
**GET MOVIES/ID**
```markdown
GET /movies/id - Retrieve a single movie by id.
```

```json
{
    "id": "abffc6bd-8dcc-4f10-8ad0-9c5ec1c13059",
    "title": "O Garfild",
    "subTitle": "O gato sapéca",
    "yearOfRelease": 2012,
    "imgUrl": "www.img/url.com",
    "synopsis": "Um gato para lá de esperto",
    "genre": {
        "id": "f891c878-9f2d-494d-bb9c-76d74824387d",
        "name": "Comédia",
        "movies": [],
        "reviews": [],
        "links": []
    },
    "reviews": [
        {
            "id": "f02ddf59-7e76-4717-89b3-c4cbb87a8b6b",
            "text": "Muito engraçado",
            "user": {
                "id": "f0a0967b-4d58-45f6-b743-d26b20e3e88f",
                "name": "Nanci",
                "email": "nanci@gmail.com",
                "roles": [],
                "links": []
            }
        }
        ]
}
```
**POST MOVIES**
```markdown
POST /movies - Register a new movie into the App
```
```json
{
    "id": "d0db3222-c586-4cc2-8107-b06b0cbedd2e",
    "title": "O Amanhecer",
    "subTitle": "Um lindo raiar do sol!",
    "yearOfRelease": 2007,
    "imgUrl": "www.img.com",
    "synopsis": "Uma profunda história!",
    "genre": {
        "id": "bf81ec99-caff-47b7-8d41-a453e9bdb550",
        "name": "Drama",
        "movies": [],
        "reviews": [],
        "links": []
    },
    "reviews": [],
    "links": []
}
```
**PUT MOVIES**
```markdown
PUT/movies/id - Update a movie in the application by id.
```
```json
{
    "id": "d0db3222-c586-4cc2-8107-b06b0cbedd2e",
    "title": "O anoitecer",
    "subTitle": "Um belo por do sol!",
    "yearOfRelease": 2007,
    "imgUrl": "www.img.com",
    "synopsis": "Uma linda e maravilhosa noite!",
    "genre": {
        "id": "192828c3-1e10-408e-90fe-ca3fba05c790",
        "name": "Aventura",
        "movies": [],
        "reviews": [],
        "links": []
    },
    "reviews": [],
    "links": []
}
```
**DELETE MOVIES**
```markdown
DELETE/movies/id - Delete a movie in the application by id.
return HTTP status: 200.
body: Movie deleted successfully.

```
## Database
The project utilizes [H2 Database](https://www.h2database.com/html/tutorial.html) as the database.

## Technologies Used

- Java version 17
- Spring Boot Version 3.3.4
- Maven
- H2 Database
- IntelliJ IDEA Community
- Postman

## Observation
This APIRest provides other endpoints besides movies, such as:

/genres

/reviews

/users

Located in the Application resources layer.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, abra um problema ou envie uma solicitação pull ao repositório.

Ao contribuir para este projeto, siga o estilo de código existente, [convenções de commit](https://medium.com/linkapi-solutions/conventional-commits-pattern-3778d1a1e657), e envie suas alterações em uma branch separado.

![Java img](https://wallpapercave.com/wp/wp4048617.jpg)
