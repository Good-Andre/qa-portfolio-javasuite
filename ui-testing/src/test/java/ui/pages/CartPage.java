package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object для страницы корзины SauceDemo.
 */
public class CartPage {
    private final SelenideElement checkoutButton = $("#checkout");

    /**
     * Нажимает кнопку Checkout и возвращает Page Object шага 1 оформления.
     */
    public CheckoutStepOnePage clickCheckout() {
        checkoutButton.click();
        return new CheckoutStepOnePage();
    }
}
