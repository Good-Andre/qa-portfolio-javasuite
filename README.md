# 🎯 Comprehensive QA Engineering Portfolio (Java Suite)

[![Java CI with Maven](https://github.com/Good-Andre/qa-portfolio-javasuite/actions/workflows/qa-portfolio-cicd.yml/badge.svg)](https://github.com/Good-Andre/qa-portfolio-javasuite/actions)
![Java Version](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openid)
![Testing Framework](https://img.shields.io/badge/Stack-REST%20Assured%20%7C%20Selenide%20%7C%20JUnit%205-blue?style=for-the-badge)
![Report](https://img.shields.io/badge/Reports-Allure-yellow?style=for-the-badge&logo=qameta)

Добро пожаловать в мой инженерный портфолио-репозиторий. Проект демонстрирует практическое применение современных подходов к автоматизации тестирования (API & UI), разработке тестовой документации, кроссплатформенного Game QA и интеграции CI/CD процессов.

---

## 🛠 Общий технологический стек

* **Languages:** Java 17, Dart
* **API Test Automation:** REST Assured, Jackson, Lombok, AspectJ
* **UI Test Automation:** Selenide (Selenium WebDriver Wrapper), Page Object Model
* **Game Test Automation:** Flutter Test, Flame Engine, Integration Test, Golden Tests
* **Assertion Libraries:** AssertJ, JUnit 5 Assertions, Flutter Test Expect
* **Test Runners & Build Tools:** JUnit 5, Apache Maven, Flutter Test
* **Reporting & CI/CD:** Allure Report, GitHub Actions, GitHub Pages
* **Test Management & Manual QA:** Test Cases, Checklists, Bug Reports (DevTools, Postman)
* **Game QA:** Test Strategy, Risk Matrix, Cross-Platform Matrix, Playtest Feedback

---

## 📂 Структура проектов и статус

| Модуль | Направление | Технологии | Статус |
| :--- | :--- | :--- | :--- |
| [**`api-testing/`**](./api-testing) | API Test Automation | Java 17, REST Assured, JUnit 5, AssertJ, Allure | 🟢 Готов |
| [**`ui-testing/`**](./ui-testing) | UI Test Automation | Java 17, Selenide, JUnit 5, Page Object Model, Allure | 🟢 Готов |
| [**`manual-testing/`**](./manual-testing) | Manual Web QA | Test Plans, Test Cases, DevTools, Postman | 🟢 Готов |
| [**`game-testing/`**](./game-testing) | Game QA Case Study | Flutter, Flame, Test Strategy, Bug Reports, Playtests | 🟢 Готов |

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

---

### 3. 📋 Manual Web QA (`/manual-testing`)

Ручное тестирование и тестовая документация для функционала «Корзина и оформление заказа» на **SauceDemo**.

* **Фокус:** валидация UX, граничные сценарии и дефекты, которые нецелесообразно или невозможно автоматизировать (например, JS‑ошибки, специфические UX‑проблемы).
* **Артефакты:** чек‑листы с матрицей рисков, примеры баг‑репортов с приоритизацией, критерии приёмки (DoD), матрица трассировки ручных кейсов к автотестам.
* **Техники тест‑дизайна:** эквивалентное разделение, анализ граничных значений, предугадывание ошибок, сквозное E2E‑тестирование, таблицы решений для валидации форм.
* **Бизнес‑ценность:** снижение рисков потери заказов из‑за багов в корзине, обеспечение корректного расчёта итоговой суммы и поддержание доверия пользователей к интерфейсу.
* **Статус покрытия:** 50% критических кейсов автоматизировано; оставшиеся ручные кейсы — из‑за низкого ROI автоматизации либо из‑за активных дефектов (ожидают исправления).
* **Быстрый запуск:** артефакты находятся в папке manual-testing/ — это статические документы (Markdown), не требуют запуска тестов.

---

### 4. 🎮 Game QA Case Study (`/game-testing`)

Полный цикл QA-инженерии для кроссплатформенной пазл-игры на **Flutter + Flame** (Web и Windows Desktop). Проект построен как реальный Case Study: от стратегии до плейтестов и автоматизации.

* **Документация (6 артефактов):** Test Strategy с риск-матрицей, чек-лист игровых механик (Drag & Drop, коллизии, win/lose conditions), кроссплатформенная матрица (Web vs Windows), баг-репорты с аппаратными данными, обратная связь от плейтестов, план автоматизации на Dart.
* **Специфика Game QA:** игровой цикл (`update()` / `render()`), lifecycle Flame-компонентов, обработка прерываний (пауза, сворачивание, потеря фокуса), сохранение прогресса.
* **Сравнение графических движков:** методология тестирования Impeller vs Skia на Windows с метриками FPS, потребления памяти и визуальных артефактов.
* **План автоматизации:** unit-тесты (ScoreManager, GameState), widget-тесты (Overlay-экраны), golden-тесты (визуальная регрессия на разных разрешениях), integration-тесты (E2E игровой цикл), performance-тесты (FPS benchmark).
* **Быстрый запуск:** QA-документация в папке `game-testing/` — статические Markdown-файлы. Автотесты на Dart выполняются в рамках Flutter-проекта (`flutter test`).

---

## 🔄 CI/CD

Проект использует единый GitHub Actions workflow (`qa-portfolio-cicd.yml`):

* **Параллельный запуск** API и UI тестов
* **Объединённый Allure-отчёт** публикуется на GitHub Pages
* **Артефакты** (сырые результаты, HTML-отчёт) доступны на вкладке Actions

---

## 🚀 Быстрый старт

```bash
# API тесты
cd api-testing && mvn clean test allure:serve

# UI тесты
cd ui-testing && mvn clean test allure:serve

# Game QA — документация (Markdown)
cd game-testing && cat README.md

# Game QA — автотесты (Dart, в рамках Flutter-проекта)
flutter test test/game/           # unit-тесты
flutter test test/golden/         # golden-тесты
flutter test integration_test/   # E2E-тесты
```

---

## 📬 Контакты

[![GitHub](https://img.shields.io/badge/GitHub-Good--Andre-black?style=flat-square&logo=github)](https://github.com/Good-Andre)
