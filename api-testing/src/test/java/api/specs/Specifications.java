package api.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Фабрика спецификаций RestAssured.
 * Исключает дублирование настроек таймаутов, хедеров и логирования.
 */
public class Specifications {
    private static final String BASE_URL = "https://reqres.in";

    /**
     * Базовая спецификация запроса. Включает автоматическое прикрепление
     * логов запросов/ответов к отчету Allure via AllureRestAssured filter.
     */
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder().setBaseUri(BASE_URL).setContentType(ContentType.JSON).addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    /**
     * Динамическая спецификация ответа с проверкой статус-кода.
     */
    public static ResponseSpecification responseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder().expectStatusCode(expectedStatusCode).log(LogDetail.ALL).build();
    }
}
