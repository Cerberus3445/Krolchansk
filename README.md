# 🚀 Krolchansk

**Krolchansk** — современное веб-приложение на базе **Spring Boot**, представляющее собой полноценный портал для работы с каталогом товаров и услуг, оформления заказов и управления системой через защищённую административную панель.

Проект ориентирован на простоту запуска, безопасность и удобство масштабирования. Подходит как для учебных целей, так и в качестве основы для коммерческих решений.

---

## ✨ Основные возможности

* 🏠 Приветственная главная страница
* 📦 Просмотр каталога товаров и услуг
* 🛒 Оформление и отправка заказов
* 🔐 Защищённая административная панель
* 🤖 Защита форм с помощью **Yandex SmartCaptcha**
* 📁 Поддержка загрузки файлов (multipart)
* 🌍 Интернационализация (i18n)
* 📑 Swagger-документация API
* 📊 Мониторинг через Spring Boot Actuator
* ⚡ Кэширование с использованием Caffeine
* 🔄 Миграции базы данных через Flyway

---

## 🔒 Безопасность

Приложение изначально спроектировано с учётом современных требований безопасности:

* Полная защита с помощью **Spring Security**
* Защита от **CSRF-атак**
* Предотвращение:

   * XSS-атак
   * Clickjacking
   * других распространённых уязвимостей
* Надёжная система аутентификации и авторизации

---

## ⚡ Производительность и DX

* ⚡ Кэширование через **Caffeine** для ускорения работы
* 🔄 Автоматические миграции БД через **Flyway**
* 🪶 Лёгкая встроенная база данных **SQLite**
* 🧩 Чистая архитектура и удобная структура проекта

---

## 🛠 Технологический стек

* **Java 21**
* **Spring Boot 3.x**
* Spring Web MVC + Thymeleaf
* Spring Security
* Spring Data JPA + Hibernate
* SpringDoc OpenAPI (Swagger UI)
* SQLite
* Flyway
* Lombok + MapStruct
* Caffeine Cache
* Maven
* Yandex SmartCaptcha

---

## 📁 Структура проекта

```bash
Krolchansk/
├── src/main/java/ru/krolchansk/     # Исходный код
│   ├── domain/                     # Сущности и бизнес-логика
│   ├── infrastructure/             # Репозитории, конфигурации, утилиты
│   └── web/
│       ├── controller/             # MVC-контроллеры
│       └── advice/                 # Глобальная обработка ошибок
├── src/main/resources/
│   ├── templates/                  # Thymeleaf-шаблоны
│   ├── static/                     # CSS, JS, изображения
│   ├── db/migration/               # Flyway-миграции
│   ├── application.yml             # Dev-конфигурация
│   ├── application-prod.yml        # Prod-конфигурация
│   └── messages.properties         # Файлы локализации
├── data/                           # SQLite база данных
├── amvera.yml                      # Конфигурация деплоя
├── pom.xml
├── mvnw & mvnw.cmd                 # Maven Wrapper
└── README.md
```

---

## 🚀 Запуск проекта

### 1. Клонирование репозитория

```bash
git clone https://github.com/Cerberus3445/Krolchansk.git
cd Krolchansk
```

### 2. Сборка проекта

```bash
./mvnw clean package
```

### 3. Запуск приложения

```bash
./mvnw spring-boot:run
```

или через JAR:

```bash
java -jar target/*.jar
```

📍 После запуска приложение будет доступно по адресу:
**http://localhost:8080**

---

## 🌐 API и документация

* Swagger UI:
  http://localhost:8080/swagger-ui.html

* OpenAPI JSON:
  http://localhost:8080/v3/api-docs

---

## 🔐 Административная панель

Доступна после авторизации.
Учётные данные задаются в конфигурационных файлах приложения.

---

## ⚙️ Конфигурация

* Основные настройки:

   * `application.yml`
   * `application-prod.yml`

* База данных:

  ```
  jdbc:sqlite:data/krolchansk.db
  ```

* Профиль по умолчанию:
  `prod`

* Yandex SmartCaptcha:

   * ключи указываются в конфигурации приложения

---


## 👤 Авторы

**Egor Malov** - разработка приложения

**Polina Plotnikova** - дизайн приложения

---

## 💡 Дополнительно

Krolchansk может использоваться как:

* основа для e-commerce проекта
* админ-панель для внутренних сервисов
* учебный пример современной Spring Boot архитектуры

---
