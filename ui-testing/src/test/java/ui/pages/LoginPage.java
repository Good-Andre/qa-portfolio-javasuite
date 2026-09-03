package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для страницы логина SauceDemo.
 */
public class LoginPage {
    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement errorMessage = $("[data-test='error']");

    /**
     * Открывает страницу и возвращает текущий Page Object.
     */
    public LoginPage openPage() {
        open("https://saucedemo.com");
        return this;
    }

    /**
     * Выполняет вход и возвращает страницу каталога товаров.
     * Предполагается, что после успешного входа происходит редирект.
     */
    public ProductsPage loginAs(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        return new ProductsPage();
    }

    /**
     * Возвращает текст сообщения об ошибке, если оно есть.
     */
    public String getErrorMessageText() {
        return errorMessage.getText();
    }
}
