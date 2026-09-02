# 🖥 UI Test Automation Suite (`ui-testing`)

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openid)
![Selenide](https://img.shields.io/badge/Selenide-7.5.0-43B02A?style=flat-square)
![JUnit 5](https://img.shields.io/badge/JUnit-5.10.2-25A162?style=flat-square&logo=junit5)
![Allure](https://img.shields.io/badge/Allure_Report-2.25.0-yellow?style=flat-square&logo=qameta)

Фреймворк автоматизации UI-тестирования для сервиса [SauceDemo](https://saucedemo.com). Проект построен по паттерну Page Object Model с разделением на слои Pages, Steps и Tests, поддерживает расширенную Allure-отчётность и готов к запуску в CI/CD (headless-режим).

---

## 🛠 Технологический стек

* **Язык программирования:** Java 17
* **Тестовый фреймворк:** JUnit 5 (Jupiter)
* **UI-автоматизация:** Selenide (обёртка над Selenium WebDriver)
* **Управление драйверами:** SeleniumManager (встроенный в Selenium 4)
* **Ассерции:** AssertJ (Fluent Assertions)
* **Отчётность:** Allure Framework + AspectJ Weaver (для перехвата `@Step`)
* **Сборщик:** Apache Maven

---

## 🏗 Архитектура и шаблоны проектирования

Проект разделён по слоям для обеспечения масштабируемости и читаемости тестов:

```text
src/test/java/ui/
├── base/          # BaseTest — глобальная конфигурация Selenide (браузер, headless, таймауты)
├── data/          # TestData — централизованное хранение тестовых данных
├── pages/         # Page Objects (LoginPage, ProductsPage) — локаторы и действия со страницами
├── steps/         # Steps Layer — шаги взаимодействия с UI + Allure @Step
└── tests/         # Тестовые сценарии (Clean Tests + AssertJ + Allure-аннотации)
