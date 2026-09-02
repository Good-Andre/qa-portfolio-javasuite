package ui.tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.base.BaseTest;
import ui.data.TestData;
import ui.steps.LoginSteps;
import ui.steps.ProductsSteps;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("UI Automation Suite")
@Feature("SauceDemo Login & Cart")
public class SauceDemoTests extends BaseTest {

    @Test
    @Tag("ui")
    @Story("Авторизация")
    @DisplayName("Успешный вход стандартного пользователя")
    void successLoginTest() {
        var productsPage = LoginSteps.login(TestData.STANDARD_USER, TestData.STANDARD_PASSWORD);

        productsPage.getTitleElement().shouldBe(Condition.visible);
        assertThat(productsPage.getTitleElement().getText())
                .as("Заголовок страницы должен быть 'Products'")
                .isEqualTo("Products");
    }

    @Test
    @Tag("ui")
    @Story("Авторизация")
    @DisplayName("Ошибка при неверных данных/заблокированном пользователе")
    void failedLoginTest() {
        LoginSteps.login(TestData.LOCKED_OUT_USER, TestData.WRONG_PASSWORD);

        String errorMessage = LoginSteps.getErrorMessage();
        assertThat(errorMessage)
                .as("Сообщение об ошибке должно соответствовать ожидаемому")
                .isEqualTo(TestData.EXPECTED_ERROR_MESSAGE);
    }

    @Test
    @Tag("ui")
    @Story("Корзина")
    @DisplayName("Добавление товара в корзину и проверка бейджа")
    void addProductToCartTest() {
        var productsPage = LoginSteps.login(TestData.STANDARD_USER, TestData.STANDARD_PASSWORD);

        ProductsSteps.addBackpackToCart();

        var badge = productsPage.getCartBadgeValue();
        assertThat(badge)
                .as("В корзине должен быть 1 товар")
                .isEqualTo("1");
    }
}
