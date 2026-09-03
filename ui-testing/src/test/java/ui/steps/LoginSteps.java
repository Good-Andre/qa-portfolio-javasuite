package ui.steps;

import io.qameta.allure.Step;
import ui.pages.LoginPage;
import ui.pages.ProductsPage;

/**
 * Слой шагов для действий на странице логина.
 */
public class LoginSteps {

    @Step("Открыть страницу логина")
    public static LoginPage openLoginPage() {
        return new LoginPage().openPage();
    }

    @Step("Выполнить вход с логином {username} и паролем {password}")
    public static ProductsPage login(String username, String password) {
        return openLoginPage().loginAs(username, password);
    }

    @Step("Получить текст сообщения об ошибке")
    public static String getErrorMessage() {
        return new LoginPage().getErrorMessageText();
    }
}
