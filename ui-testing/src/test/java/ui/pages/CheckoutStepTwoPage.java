package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object для шага 2 оформления заказа (обзор и подтверждение).
 */
public class CheckoutStepTwoPage {
    private final SelenideElement finishButton = $("#finish");
    private final SelenideElement subtotalLabel = $(".summary_subtotal_label");
    private final SelenideElement taxLabel = $(".summary_tax_label");
    private final SelenideElement totalLabel = $(".summary_total_label");

    /**
     * Подтверждает заказ и переходит на финальную страницу.
     */
    public CheckoutCompletePage clickFinish() {
        finishButton.click();
        return new CheckoutCompletePage();
    }

    /**
     * Получает текст суммы до вычета налогов.
     */
    public String getSubtotalText() {
        return subtotalLabel.getText();
    }

    /**
     * Получает текст налога.
     */
    public String getTaxText() {
        return taxLabel.getText();
    }

    /**
     * Получает итоговую сумму.
     */
    public String getTotalText() {
        return totalLabel.getText();
    }
}
