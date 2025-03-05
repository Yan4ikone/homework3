package in.reqres;

import data.*;
import helpers.Assertions;
import helpers.DataProviders;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static specifications.Specifications.*;

/**
 * Тесты для проверки функционала работы с API.
 * @author Yan
 */
public class TestsApi extends BaseTest {

    /**
     * Проверяется, что имена файлов аватаров пользователей уникальны.
     */
    @Test
    public void testOne() {
        installSpec(responseSpec200());
        UserResponseDto userRes = given()
                .when()
                .get("api/users?page=2")
                .then()
                .body("page", notNullValue()) // проверка body на не нулевое значение
                .spec(responseSpec200()) // проверка получения ожидаемого status code
                .extract().body().as(UserResponseDto.class);
        List<String> avatarUrl = userRes.getData().stream()
                .map(UserDto::getAvatar)
                .toList();
        Set<String> uniqueUrl = userRes.getData().stream()
                .map(UserDto::getAvatar)
                .collect(Collectors.toSet());
        Assertions.uniqueList(avatarUrl, uniqueUrl); // сравниваем идентичность двух списков
    }

    /**
     * Тест для успешной авторизации пользователя.
     * @param email Email пользователя.
     * @param password Пароль пользователя.
     */
    @Test(dataProvider = "successLoginData", dataProviderClass = DataProviders.class)
    public void loginUser(String email, String password) {
        LoginDto loginDto = new LoginDto(email, password);
        installSpec(responseSpec200());
        LoginResponseDto loginResponseDto = given()
                .when()
                .post("api/login")
                .then()
                .body("token", notNullValue()) // проверка body на не нулевое значение
                .spec(responseSpec200())
                .extract().body().as(LoginResponseDto.class);
        Assertions.assertRegistrationSuccess(loginResponseDto);
        Assertions.assertRegistrationCorrect(email, password, loginDto);
    }

    /**
     * Тест для успешной регистрации нового пользователя.
     * @param email Email пользователя.
     * @param password Пароль пользователя.
     */
    @Test(dataProvider = "failureLoginData", dataProviderClass = DataProviders.class)
    public void loginFailureUser(String email, String password) {
        installSpec(responseSpec400());
        LoginDto loginDto = new LoginDto(email, password);
        given()
                .when()
                .post("api/login")
                .then()
                .spec(responseSpec400())
                .extract().response();
        Assertions.assertRegistrationCorrect(email, password, loginDto);
        Assertions.assertRegistrationFailure(loginDto);
    }

    /**
     * Тест для проверки, что операция LIST <RESOURCE> возвращает данные отсортированные по годам.
     * Проверяется, что список годов отсортирован.
     */
    @Test
    public void testOnAYear() {
        installSpec(responseSpec200());
        ListDto response = given()
                .when()
                .get("api/unknown")
                .then()
                .log().body()
                .spec(responseSpec200())
                .extract().body().as(ListDto.class);
        List<Integer> years = new ArrayList<>(response.getData()
                .stream()
                .map(DataDto::getYear)
                .toList());
        Assertions.isSorted(years);
    }

    /**
     * Тест для проверки ответа от сервиса https://gateway.autodns.com/.
     * Проверяется количество тегов в ответе.
     */
    @Test
    public void testGateway() {
        installSpec(responseSpec200());
        Response response = given()
                .when()
                .get("https://gateway.autodns.com/")
                .then()
                .log().body()
                .spec(responseSpec200())
                .extract().response();
        String responseBody = response.asString();
        Pattern pattern = Pattern.compile("<(\\?*)(\\w+)(\\s|>)");
        Matcher matcher = pattern.matcher(responseBody);
        int tagCount = 0;
        while (matcher.find()) {
            tagCount++;
        }
        Assertions.assertTegs(tagCount);
    }
}

