<h1>🚀 Разработка Системы Управления Банковскими Картами</h1>

<h2>📁 Инструкция по запуску</h2>

- Скачать проект
- Скачать и установить Docker
- Создать файл .env в корне проекта
    - В файле указать параметры для запуска
    - DB_PORT=порт для подключения к БД
    - DB_NAME=название БД
    - DB_USER=имя пользователя БД
    - DB_PASSWORD=Пароль для БД
    - JWT_SECRET=ваш секретный ключ для JWT токена
    - JWT_EXPIRED=время жизни токена в минутах
    - SERVER_PORT=порт на котором будет запущен сервер
    - OPENAPI_URL=url для доступа к swagger
- Запустить docker-compose.yaml
    - команда дял запуска: docker-compose up

<h2>Тестовые данные</h2>

- DB_PORT=5432
- DB_NAME=test_db
- DB_USER=testuser
- DB_PASSWORD=password
- JWT_SECRET=secretKey
- JWT_EXPIRED=20
- SERVER_PORT=8080
- OPENAPI_URL=http://localhost:8080/

<h2>💡 Технологии</h2>

- Язык программирования: ![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
- Сборка и зависимости: ![Maven](https://img.shields.io/badge/Maven-3.8.1-blue?logo=apachemaven)
- Фреймворк: ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-*?logo=spring)
- База данных: ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?logo=postgresql)
- Дополнительно:
    - Lombok ![Lombok](https://img.shields.io/badge/Lombok-1.18.36-blue)
    - JWT ![JWT](https://img.shields.io/badge/JWT-0.11.2-blue)
    - SLF4J ![SLF4J](https://img.shields.io/badge/SLF4J-2.0.16-blue)
    - Mockito ![Mockito](https://img.shields.io/badge/Mockito-5.14.2-blue)
    - Mapstruct ![MapStruct](https://img.shields.io/badge/MapStruct-1.6.3-blue)

<p>В БД добавлены тестовые данные при помощи sql скрипта запущенным через liquibase</p>

- username: ivanov@example.com
- password: 1234
- roles: USER, ADMIN