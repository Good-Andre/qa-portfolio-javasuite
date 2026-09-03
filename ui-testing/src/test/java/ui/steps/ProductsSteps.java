package ui.steps;

import io.qameta.allure.Step;
import ui.pages.ProductsPage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Шаги для работы со страницей товаров.
 */
public class ProductsSteps {

    @Step("Добавить рюкзак в корзину")
    public static ProductsPage addBackpackToCart() {
        return new ProductsPage().addBackpackToCart();
    }

    @Step("Проверить значение бейджа корзины: ожидается {expectedValue}")
    public static void verifyCartBadge(String expectedValue) {
        String actual = new ProductsPage().getCartBadgeValue();
        assertThat(actual).as("Бейдж корзины должен соответствовать ожидаемому").isEqualTo(expectedValue);
    }
}
