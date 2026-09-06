package ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Базовый класс для UI-тестов.
 * Выносит общую конфигурацию браузера.
 */
public class BaseTest {

    @BeforeAll
    static void globalSetup() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = true;
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 30000;
        Configuration.pollingInterval = 200;
        System.setProperty("selenide.driverManagerEnabled", "true");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        Configuration.browserCapabilities = options;
    }

    @BeforeEach
    void perTestSetup() {
        /// WebDriverRunner.getWebDriver().manage().deleteAllCookies();
        /// При необходимости можно также сбросить localStorage/sessionStorage через JS
    }
}
