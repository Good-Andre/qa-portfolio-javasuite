package ui.base;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * Базовый класс для UI-тестов.
 * Выносит общую конфигурацию браузера.
 */
public class BaseTest {

    @BeforeAll
    static void globalSetup() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        // для локальной отладки false
        Configuration.headless = true;
        Configuration.timeout = 10000;
        Configuration.pollingInterval = 500;
        System.setProperty("selenide.driverManagerEnabled", "true");
    }

    @BeforeEach
    void perTestSetup() {
        /// Опционально: очистка куки/localStorage перед каждым тестом
        /// com.codeborne.selenide.WebDriverRunner.getWebDriver().manage().deleteAllCookies();
    }
}
