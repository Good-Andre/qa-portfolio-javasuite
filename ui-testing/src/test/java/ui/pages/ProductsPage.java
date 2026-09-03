package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object для страницы каталога товаров SauceDemo.
 */
public class ProductsPage {
    private final SelenideElement title = $(".title");
    private final SelenideElement shoppingCartBadge = $(".shopping_cart_badge");
    private final SelenideElement cartLink = $(".shopping_cart_link");

    public SelenideElement getTitleElement() {
        return title;
    }

    /**
     * Добавляет рюкзак в корзину и возвращает текущий объект страницы.
     */
    public ProductsPage addBackpackToCart() {
        SelenideElement addButton = $("[data-test='add-to-cart-sauce-labs-backpack']");
        SelenideElement removeButton = $("[data-test='remove-sauce-labs-backpack']");

        if (addButton.isDisplayed()) {
            addButton.shouldBe(Condition.visible, Condition.enabled).click();
        }

        return this;
    }

    /**
     * Возвращает значение бейджа корзины (число товаров).
     */
    public String getCartBadgeValue() {
        return shoppingCartBadge.getText();
    }

    /**
     * Переходит в корзину и возвращает Page Object корзины.
     */
    public CartPage clickCartIcon() {
        cartLink.click();
        return new CartPage();
    }
}
