package ui.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object для страницы товаров.
 */
public class ProductsPage {
    private final SelenideElement title = $(".title");
    private final SelenideElement addToCartBackpackButton = $("#add-to-cart-sauce-labs-backpack");
    private final SelenideElement shoppingCartBadge = $(".shopping_cart_badge");

    public SelenideElement getTitleElement() {
        return title;
    }

    public ProductsPage addBackpackToCart() {
        addToCartBackpackButton.click();
        return this;
    }

    public String getCartBadgeValue() {
        return shoppingCartBadge.getText();
    }
}
