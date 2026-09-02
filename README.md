# 🎯 Comprehensive QA Engineering Portfolio (Java Suite)

[![Java CI with Maven](https://github.com/Good-Andre/qa-portfolio-javasuite/actions/workflows/qa-portfolio-cicd.yml/badge.svg)](https://github.com/Good-Andre/qa-portfolio-javasuite/actions)
![Java Version](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openid)
![Testing Framework](https://img.shields.io/badge/Stack-REST%20Assured%20%7C%20Selenide%20%7C%20JUnit%205-blue?style=for-the-badge)
![Report](https://img.shields.io/badge/Reports-Allure-yellow?style=for-the-badge&logo=qameta)

Добро пожаловать в мой инженерный портфолио-репозиторий. Проект демонстрирует практическое применение современных подходов к автоматизации тестирования (API & UI), разработке тестовой документации и интеграции CI/CD процессов.

---

## 🛠 Общий технологический стек

* **Languages:** Java 17
* **API Test Automation:** REST Assured, Jackson, Lombok, AspectJ
* **UI Test Automation:** Selenide (Selenium WebDriver Wrapper), Page Object Model
* **Assertion Libraries:** AssertJ, JUnit 5 Assertions
* **Test Runners & Build Tools:** JUnit 5, Apache Maven
* **Reporting & CI/CD:** Allure Report, GitHub Actions, GitHub Pages
* **Test Management & Manual QA:** Test Cases, Checklists, Bug Reports (DevTools, Postman)

---

## 📂 Структура проектов и статус

| Модуль | Направление | Технологии | Статус |
| :--- | :--- | :--- | :--- |
| [**`api-testing/`**](./api-testing) | API Test Automation | Java 17, REST Assured, JUnit 5, AssertJ, Allure | 🟢 Готов |
| [**`ui-testing/`**](./ui-testing) | UI Test Automation | Java 17, Selenide, JUnit 5, Page Object Model, Allure | 🟢 Готов |
| [**`manual-testing/`**](./manual-testing) | Manual Web QA | Test Plans, Test Cases, DevTools, Postman | 🟡 В разработке |
| [**`game-testing/`**](./game-testing) | Game QA & Bug Tracking | Game Testing, Exploratory Testing, Issue Tracking | 🟡 В разработке |

---

## 📑 Детальное описание проектов

### 1. 🌐 API Test Automation (`/api-testing`)

Автоматизация тестирования REST API сервиса **ReqRes**.

* **Архитектурные паттерны:** API Client Layer (`Steps`), DTO pattern (Lombok + Jackson), Factory Specification pattern.
* **Особенности:** Глобальная защита от несовместимости JSON-схем, интеграция `AllureRestAssured` и `AspectJ` для пошаговой детализации отчетов, использование fluent assertions (`AssertJ`).
* **Быстрый запуск:**

  ```bash
  cd api-testing && mvn clean test allure:serve
  ```

---

### 2. 🖥 UI Test Automation (`/ui-testing`)

Автоматизация тестирования пользовательского интерфейса веб‑приложения **SauceDemo**.

* **Архитектурные паттерны:** Page Object Model (классы страниц), Steps Layer (бизнес‑шаги с аннотациями `@Step`), разделение тестовых данных (TestData), базовый класс `BaseTest` для единой конфигурации Selenide.
* **Особенности:** автоматическое управление ChromeDriver через SeleniumManager (без ручного скачивания драйверов), headless‑режим для CI/CD, расширенная Allure‑отчётность (`@Epic`, `@Feature`, `@Severity`, `@DisplayName`), fluent‑ассерции на базе AssertJ.
* **Покрытие:** сценарии успешной и неуспешной авторизации, добавление товара в корзину и проверка состояния UI‑элементов.
* **Быстрый запуск:**

  ```bash
  cd ui-testing && mvn clean test allure:serve
  ```

### 3. 📋 Manual Web QA (`/manual-testing`)

*В разработке.* Тестовая документация для веб-приложения: тест-кейсы, чек-листы, баг-репорты.

### 4. 🎮 Game QA & Bug Tracking (`/game-testing`)

*В разработке.* Тестирование пазл-игры: исследовательское тестирование, баг-трекинг, отчетность.

---

## 🔄 CI/CD

Проект использует единый GitHub Actions workflow (`qa-portfolio-cicd.yml`):

* **Параллельный запуск** API и UI тестов
* **Объединённый Allure-отчёт** публикуется на GitHub Pages
* **Артефакты** (сырые результаты, HTML-отчёт) доступны на вкладке Actions

---

## 🚀 Быстрый старт (оба проекта)

```bash
# API тесты
cd api-testing && mvn clean test allure:serve

# UI тесты
cd ui-testing && mvn clean test allure:serve
```

---

## 📬 Контакты

[![GitHub](https://img.shields.io/badge/GitHub-Good--Andre-black?style=flat-square&logo=github)](https://github.com/Good-Andre)
