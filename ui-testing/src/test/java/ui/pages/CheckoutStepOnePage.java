package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object для шага 1 оформления заказа (ввод данных пользователя).
 */
public class CheckoutStepOnePage {
    private final SelenideElement firstNameInput = $("#first-name");
    private final SelenideElement lastNameInput = $("#last-name");
    private final SelenideElement postalCodeInput = $("#postal-code");
    private final SelenideElement continueButton = $("#continue");
    private final SelenideElement errorContainer = $("[data-test='error']");

    /**
     * Заполняет поля формы и возвращает текущий объект.
     */
    public CheckoutStepOnePage fillForm(String firstName, String lastName, String postalCode) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        postalCodeInput.setValue(postalCode);
        return this;
    }

    /**
     * Нажимает Continue и переходит на шаг 2 (обзор заказа).
     */
    public CheckoutStepTwoPage clickContinue() {
        continueButton.click();
        return new CheckoutStepTwoPage();
    }

    /**
     * Нажимает Continue без заполнения полей (для проверки валидации).
     * Остаётся на текущей странице.
     */
    public void clickContinueWithError() {
        continueButton.click();
    }

    /**
     * Возвращает текст ошибки валидации.
     */
    public String getErrorMessage() {
        return errorContainer.getText();
    }
}
