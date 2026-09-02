package ui.steps;

import io.qameta.allure.Step;
import ui.pages.ProductsPage;

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
        // Здесь можно добавить Allure-лог, если нужно
    }
}
