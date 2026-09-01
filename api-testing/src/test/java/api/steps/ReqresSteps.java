package api.steps;

import api.models.LoginRequest;
import api.models.LoginResponse;
import api.models.UnsuccessfulLogin;
import api.models.UserData;
import api.specs.Specifications;
import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Слой API-клиента (Steps).
 * Инкапсулирует работу с эндпоинтами. Аннотации @Step формируют
 * красивый и понятный дерево-отчет в Allure.
 */
public class ReqresSteps {
    @Step("Отправка запроса на успешную авторизацию пользователем: {request.email}")
    public static LoginResponse loginSuccess(LoginRequest request) {
        return given().spec(Specifications.requestSpec()).body(request).when().post("/api/login").then().spec(Specifications.responseSpec(200)).extract().as(LoginResponse.class);
    }

    @Step("Отправка запроса на негативную авторизацию (без пароля)")
    public static UnsuccessfulLogin loginUnsuccessful(LoginRequest request) {
        return given().spec(Specifications.requestSpec()).body(request).when().post("/api/login").then().spec(Specifications.responseSpec(400)).extract().as(UnsuccessfulLogin.class);
    }

    @Step("Получение списка пользователей со страницы {page}")
    public static List<UserData> getUsersList(int page) {
        return given().spec(Specifications.requestSpec()).queryParam("page", page).when().get("/api/users").then().spec(Specifications.responseSpec(200)).extract().jsonPath().getList("data", UserData.class);
    }
}
