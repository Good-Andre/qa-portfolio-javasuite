package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object для финальной страницы успешного оформления заказа.
 */
public class CheckoutCompletePage {
    private final SelenideElement completeHeader = $(".complete-header");

    public String getCompleteHeaderText() {
        return completeHeader.getText();
    }

    public boolean isCompleteHeaderVisible() {
        return completeHeader.isDisplayed();
    }
}
