package ui.data;

/**
 * Тестовые данные для SauceDemo.
 * В реальном проекте можно вынести в YAML/JSON или в отдельный конфиг.
 */
public final class TestData {
    public static final String STANDARD_USER = "standard_user";
    public static final String STANDARD_PASSWORD = "secret_sauce";

    public static final String LOCKED_OUT_USER = "locked_out_user";
    public static final String WRONG_PASSWORD = "wrong_password";

    public static final String EXPECTED_ERROR_MESSAGE =
            "Epic sadface: Username and password do not match any user in this service";

    private TestData() {
        /// утилитарный класс
    }
}
