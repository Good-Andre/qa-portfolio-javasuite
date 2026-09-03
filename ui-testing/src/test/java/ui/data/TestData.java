package ui.data;

/**
 * Тестовые данные для SauceDemo.
 * <p>
 * В реальном проекте такие данные лучше выносить в YAML/JSON или конфиг-файлы.
 * Здесь храним как константы для простоты и скорости запуска автотестов.
 */
public final class TestData {
    // Учётные данные
    public static final String STANDARD_USER = "standard_user";
    public static final String STANDARD_PASSWORD = "secret_sauce";

    public static final String LOCKED_OUT_USER = "locked_out_user";
    public static final String WRONG_PASSWORD = "wrong_password";

    // Сообщения об ошибках
    public static final String EXPECTED_ERROR_MESSAGE = "Epic sadface: Username and password do not match any user in this service";

    // Данные для Checkout
    public static final String FIRST_NAME = "Ivan";
    public static final String LAST_NAME = "Petrov";
    public static final String POSTAL_CODE = "424000";

    // Ожидаемые сообщения валидации и успеха
    public static final String EXPECTED_CHECKOUT_ERROR = "Error: First Name is required";
    public static final String EXPECTED_COMPLETE_MESSAGE = "Thank you for your order!";

    private TestData() {
        // Утилитарный класс — запрещаем инстанцирование
    }
}
