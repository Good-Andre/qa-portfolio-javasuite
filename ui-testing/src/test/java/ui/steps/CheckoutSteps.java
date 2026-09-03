package ui.steps;

import io.qameta.allure.Step;
import ui.pages.CheckoutCompletePage;
import ui.pages.CheckoutStepOnePage;
import ui.pages.CheckoutStepTwoPage;

/**
 * Шаги для оформления заказа: заполнение формы, подтверждение, валидация ошибок.
 */
public class CheckoutSteps {

    @Step("Заполнить форму checkout: First Name = {firstName}, Last Name = {lastName}, ZIP = {postalCode}")
    public static CheckoutStepTwoPage fillCheckoutFormAndContinue(String firstName, String lastName, String postalCode) {
        CheckoutStepOnePage form = new CheckoutStepOnePage();
        form.fillForm(firstName, lastName, postalCode);
        return form.clickContinue();
    }

    @Step("Отправить пустую форму checkout и получить сообщение об ошибке")
    public static String submitEmptyFormAndGetError() {
        CheckoutStepOnePage form = new CheckoutStepOnePage();
        form.clickContinueWithError();
        return form.getErrorMessage();
    }

    @Step("Завершить заказ (нажать Finish)")
    public static CheckoutCompletePage finishOrder(CheckoutStepTwoPage stepTwo) {
        return stepTwo.clickFinish();
    }
}
