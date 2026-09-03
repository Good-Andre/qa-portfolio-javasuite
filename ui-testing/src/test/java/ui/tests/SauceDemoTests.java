package ui.tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.base.BaseTest;
import ui.data.TestData;
import ui.steps.CheckoutSteps;
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
        assertThat(productsPage.getTitleElement().getText()).as("Заголовок страницы должен быть 'Products'").isEqualTo("Products");
    }

    @Test
    @Tag("ui")
    @Story("Авторизация")
    @DisplayName("Ошибка при неверных данных/заблокированном пользователе")
    void failedLoginTest() {
        LoginSteps.login(TestData.LOCKED_OUT_USER, TestData.WRONG_PASSWORD);

        String errorMessage = LoginSteps.getErrorMessage();
        assertThat(errorMessage).as("Сообщение об ошибке должно соответствовать ожидаемому").isEqualTo(TestData.EXPECTED_ERROR_MESSAGE);
    }

    @Test
    @Tag("ui")
    @Story("Корзина")
    @DisplayName("Добавление товара в корзину и проверка бейджа")
    void addProductToCartTest() {
        var productsPage = LoginSteps.login(TestData.STANDARD_USER, TestData.STANDARD_PASSWORD);

        ProductsSteps.addBackpackToCart();

        var badge = productsPage.getCartBadgeValue();
        assertThat(badge).as("В корзине должен быть 1 товар").isEqualTo("1");
    }

    @Test
    @Tag("ui")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Оформление заказа")
    @DisplayName("Полный E2E путь: добавление товара и успешный checkout")
    void checkoutFullFlowTest() {
        var productsPage = LoginSteps.login(TestData.STANDARD_USER, TestData.STANDARD_PASSWORD);

        ProductsSteps.addBackpackToCart();

        productsPage.clickCartIcon().clickCheckout();

        var checkoutStepTwo = CheckoutSteps.fillCheckoutFormAndContinue(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE);

        var completePage = CheckoutSteps.finishOrder(checkoutStepTwo);

        assertThat(completePage.isCompleteHeaderVisible()).as("Финальный экран должен отображаться").isTrue();
        assertThat(completePage.getCompleteHeaderText()).as("Сообщение об успешном заказе").isEqualTo(TestData.EXPECTED_COMPLETE_MESSAGE);
    }

    @Test
    @Tag("ui")
    @Severity(SeverityLevel.NORMAL)
    @Story("Валидация формы checkout")
    @DisplayName("Отправка пустой формы checkout — проверка сообщения об ошибке")
    void checkoutEmptyFormValidationTest() {
        var productsPage = LoginSteps.login(TestData.STANDARD_USER, TestData.STANDARD_PASSWORD);

        ProductsSteps.addBackpackToCart();

        productsPage.clickCartIcon().clickCheckout();

        String errorMessage = CheckoutSteps.submitEmptyFormAndGetError();

        assertThat(errorMessage).as("Должно появиться сообщение об ошибке: First Name is required").isEqualTo(TestData.EXPECTED_CHECKOUT_ERROR);
    }

    @Test
    @Tag("ui")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Расчёт стоимости")
    @DisplayName("Проверка корректности расчёта итоговой суммы")
    void checkoutTotalCalculationTest() {
        var productsPage = LoginSteps.login(TestData.STANDARD_USER, TestData.STANDARD_PASSWORD);

        ProductsSteps.addBackpackToCart();

        productsPage.clickCartIcon().clickCheckout();

        var checkoutStepTwo = CheckoutSteps.fillCheckoutFormAndContinue(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE);

        /// Извлечение числа из текстов: "Item total: $29.99", "Tax: $2.40", "Total: $32.39"
        double subtotal = extractPrice(checkoutStepTwo.getSubtotalText());
        double tax = extractPrice(checkoutStepTwo.getTaxText());
        double total = extractPrice(checkoutStepTwo.getTotalText());

        assertThat(total).as("Total должен равняться Item total + Tax").isEqualTo(subtotal + tax, org.assertj.core.data.Offset.offset(0.01));
    }

    private double extractPrice(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}
