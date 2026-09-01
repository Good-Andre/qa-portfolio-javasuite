# 🌐 REST API Test Automation Suite (`api-testing`)

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openid)
![REST Assured](https://img.shields.io/badge/REST_Assured-5.3.1-green?style=flat-square)
![JUnit 5](https://img.shields.io/badge/JUnit-5.10.2-25A162?style=flat-square&logo=junit5)
![AssertJ](https://img.shields.io/badge/AssertJ-3.25.3-blue?style=flat-square)
![Allure](https://img.shields.io/badge/Allure_Report-2.25.0-yellow?style=flat-square&logo=qameta)

Фреймворк автоматизации тестирования REST API для сервиса [ReqRes API](https://reqres.in/). Проект построен с соблюдением стандартов чистой архитектуры, шаблонов проектирования и расширенной отчетности.

---

## 🛠 Технологический стек

* **Язык программирования:** Java 17
* **Тестовый фреймворк:** JUnit 5 (Jupiter)
* **API клиент:** REST Assured
* **Сериализация / Десериализация:** Jackson Databind, Lombok (`@Data`, `@Builder`)
* **Ассерции:** AssertJ (Fluent Assertions)
* **Отчетность:** Allure Framework + AspectJ Weaver (для перехвата `@Step`)
* **Сборщик:** Apache Maven

---

## 🏗 Архитектура и Шаблоны Проектирования

Проект строго разделен по слоям для обеспечения масштабируемости и простоты поддержки:

```text
src/test/java/api/
├── models/        # DTO (Data Transfer Objects) с валидацией Jackson и Lombok
├── specs/         # Request & Response Specifications (Централизованные конфиги REST Assured)
├── steps/         # API Client Layer (Шаги взаимодействия с эндпоинтами + Allure @Step)
└── tests/         # Тестовые сценарии (Clean Tests + AssertJ)
