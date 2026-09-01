package api.tests;

import api.models.LoginRequest;
import api.models.LoginResponse;
import api.models.UnsuccessfulLogin;
import api.models.UserData;
import api.steps.ReqresSteps;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("REST API Testing Suite")
@Feature("Пользователи и Авторизация (Reqres API)")
public class ReqresPojoTests {
    @Test
    @Tag("api")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Авторизация")
    @DisplayName("Успешная авторизация пользователя с валидными кредами")
    @Description("Проверяет, что при передаче валидного email и пароля возвращается токен доступа.")
    public void successLoginTest() {
        /// GIVEN: Подготовка тестовых данных с использованием Lombok Builder
        LoginRequest loginData = LoginRequest.builder().email("eve.holt@reqres.in").password("cityslicka").build();

        /// WHEN: Выполнение запроса через слой шагов
        LoginResponse response = ReqresSteps.loginSuccess(loginData);

        /// THEN: Проверки с использованием AssertJ (Fluent Assertions)
        assertThat(response.getToken()).as("Токен авторизации не должен быть пустым").isNotNull().isNotEmpty().isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @Tag("api")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация")
    @DisplayName("Ошибка авторизации при отсутствии пароля")
    public void unsuccessfulLoginWithoutPasswordTest() {
        /// GIVEN: Запрос только с email
        LoginRequest loginData = LoginRequest.builder().email("peter@klaven").build();

        UnsuccessfulLogin response = ReqresSteps.loginUnsuccessful(loginData);

        assertThat(response.getError()).as("Текст ошибки должен сообщать об отсутствии пароля").isEqualTo("Missing password");
    }

    @Test
    @Tag("api")
    @Severity(SeverityLevel.NORMAL)
    @Story("Получение списка пользователей")
    @DisplayName("Проверка почтового домена и ID пользователей")
    public void checkUserEmailAndIdTest() {
        List<UserData> users = ReqresSteps.getUsersList(1);

        /// THEN: Fluent проверки списка
        assertThat(users).as("Список пользователей не должен быть пустым").isNotEmpty().hasSize(6);

        /// Проверка корректности домена почты у всех пользователей
        assertThat(users).allSatisfy(user -> assertThat(user.getEmail()).endsWith("@reqres.in"));

        /// Проверка параметров конкретного пользователя
        assertThat(users.get(0).getId()).as("ID первого пользователя должен быть равен 1").isEqualTo(1);
    }
}
