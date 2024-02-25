# crud-auth-api-springboot

This is a simple api for rental bike with Authentication.

## Prerequisites

- Docker
- JDK
- Intellij IDE

## Getting Started

1. **Clone the repository:**

   ```bash
   git clone https://github.com/erwinhermantodev/crud-auth-api-springboot.git
   cd crud-auth-api-springboot

   ```

2. **Running Docker compose:**

   ```bash
   docker-compose up -d
   ```


3. **Open project in Intellij IDE and Running project**

## API Documentation

The API endpoints and their usage are documented below:
### 1. Register admin

    curl --request POST \
    --url http://localhost:8080/api/users/register \
    --header 'Content-Type: application/json' \
    --cookie JSESSIONID=96E8F26BA0297B81EFFB13293C0E4FA1 \
    --data '{
    "name": "Admin",
    "email": "admin@example.com",
    "password": "password",
    "role": "ADMIN
    }'

### 2. Register user

    curl --request POST \
    --url http://localhost:8080/api/users/register \
    --header 'Content-Type: application/json' \
    --cookie JSESSIONID=96E8F26BA0297B81EFFB13293C0E4FA1 \
    --data '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password"
    }'

### 3. Login

    curl --request POST \
    --url http://localhost:8080/api/users/login \
    --header 'Content-Type: application/json' \
    --cookie JSESSIONID=96E8F26BA0297B81EFFB13293C0E4FA1 \
    --data '{
    "email": "admin@example.com",
    "password": "password"
    }'

### 4. Administrator add Bike

    curl --request POST \
    --url http://localhost:8080/api/admin/add-bike \
    --header 'Authorization: {token}' \
    --header 'Content-Type: application/json' \
    --cookie JSESSIONID=96E8F26BA0297B81EFFB13293C0E4FA1 \
    --data '{
    "brand": "Wimcycle",
    "model": "Ontel",
    "year": 2022
    }'

### 5. Administrator get list Bike

    curl --request GET \
    --url http://localhost:8080/api/admin/bikes \
    --header 'Authorization: {token}' \
    --cookie JSESSIONID=96E8F26BA0297B81EFFB13293C0E4FA1

### 6. Administrator user selection

    curl --request GET \
    --url http://localhost:8080/api/admin/user-selections \
    --header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOjIsImlhdCI6MTcwODg3MjcyMCwiZXhwIjoxNzA4OTU5MTIwfQ.gqV8jR78xNRzYu7R2zuMZ2t5CXSWlu-zmIXnD6HVeI4' \
    --cookie JSESSIONID=96E8F26BA0297B81EFFB13293C0E4FA1


### 7. User booking bike

    curl --request POST \
    --url http://localhost:8080/api/users/book-bike \
    --header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWlseUBleGFtcGxlLmNvbSIsInVzZXJfaWQiOjEsImlhdCI6MTcwODg2OTgwNywiZXhwIjoxNzA4OTU2MjA3fQ.2EA8TjY_Mgv6NI7QP4amYGKsFJ99eLgNYIlOJkDqJIg' \
    --header 'Content-Type: application/json' \
    --cookie JSESSIONID=96E8F26BA0297B81EFFB13293C0E4FA1 \
    --data '{
    "id": 1,
    "startDate": "2024-03-01",
    "endDate": "2024-03-05"
    }'