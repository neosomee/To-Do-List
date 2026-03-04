# To-Do List REST API

Небольшое, но **боевое** приложение To‑Do List на Spring Boot: задачи, категории, связь один-ко-многим, база в PostgreSQL, миграции через Liquibase и покрытие тестами (WebMvcTest + TestRestTemplate + JPA-тесты).

---

## Стек технологий

- **Java 17**
- **Spring Boot 3.5.7**
    - Spring Web (REST-контроллеры)
    - Spring Data JPA (работа с БД через репозитории)
- **PostgreSQL** — основная БД
- **H2** — in-memory БД для тестов
- **Liquibase** — миграции схемы БД
- **springdoc-openapi** — Swagger UI для документации API
- **JUnit 5**, **Spring Boot Test**, **Mockito**, **MockMvc**, **TestRestTemplate** — тестирование уровней контроллеров, сервисов и репозиториев

---

## Суть проекта

Приложение предоставляет REST API для управления списком задач:

- CRUD для **задач** (`Task`)
- CRUD для **категорий** (`Category`)
- Связь **Category (1) → Task (N)** через `category_id`
- Фильтрация задач по категории: `GET /tasks?categoryId={id}`

Используется подход:  
**Liquibase управляет схемой БД**, Hibernate — только ORM (никакого `ddl-auto=create` в проде).

---

## Структура проекта

```text
to-do-list
├── build.gradle
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.to_do_list
│   │   │       ├── ToDoListApplication.java
│   │   │       ├── controller
│   │   │       │   ├── TaskController.java        // /tasks
│   │   │       │   └── CategoryController.java    // /categories
│   │   │       ├── model
│   │   │       │   ├── Task.java                  // @ManyToOne Category
│   │   │       │   └── Category.java              // @OneToMany tasks
│   │   │       ├── repository
│   │   │       │   ├── TaskRepository.java        // findByCategoryId(...)
│   │   │       │   └── CategoryRepository.java
│   │   │       └── service
│   │   │           ├── TaskService.java           // логика задач, логи SLF4J
│   │   │           └── CategoryService.java       // логика категорий
│   │   └── resources
│   │       ├── application.properties             // Postgres, Liquibase, логирование
│   │       └── liquibase
│   │           ├── changelog-master.yml           // главный changelog
│   │           └── scripts
│   │               └── script.sql                 // Liquibase formatted SQL (таблицы, индексы, FK)
│   └── test
│       ├── java
│       │   └── com.example.to_do_list
│       │       ├── controller
│       │       │   ├── TaskControllerWebMvcTest.java
│       │       │   └── CategoryControllerWebMvcTest.java
│       │       └── repository
│       │           └── TaskRepositoryTest.java    // @DataJpaTest + H2 + Liquibase
│       └── resources
│           └── application-test.properties        // H2-конфиг для тестов
