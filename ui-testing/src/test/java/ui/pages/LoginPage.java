package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для страницы логина.
 */
public class LoginPage {
    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement errorMessage = $("[data-test='error']");

    public LoginPage openPage() {
        open("https://saucedemo.com");
        return this;
    }

    public ProductsPage loginAs(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        return new ProductsPage();
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }
}
