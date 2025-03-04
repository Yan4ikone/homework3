package in.reqres;

import data.*;
import helpers.Assertions;
import helpers.DataProviders;
import io.restassured.response.Response;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static in.reqres.BaseTest.requestSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static specifications.Specifications.*;

/**
 * Тесты для проверки функционала работы с API.
 * @author Yan
 */
public class TestsApi {

    /**
     * Проверяется, что имена файлов аватаров пользователей уникальны.
     */
    @Test
    public void testOne() {
        installSpec(responseSpec200());
        UserResponseDto userRes = given()
                .spec(requestSpec())
                .when()
                .get("api/users?page=2")
                .then()
                .log().body()
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
    @Parameters({"email", "password"})
    public void loginUser(String email, String password) {
        installSpec(responseSpec200());
        RegistrationResponseDto registrationResponseDto = given()
                .spec(requestSpec())
                .when()
                .post("api/login")
                .then()
                .log().all()
                .body("page", notNullValue()) // проверка body на не нулевое значение
                .spec(responseSpec200())
                .extract().body().as(RegistrationResponseDto.class);
        Assertions.assertRegistrationSuccess(registrationResponseDto);
    }

    /**
     * Тест для успешной регистрации нового пользователя.
     * @param email Email пользователя.
     * @param password Пароль пользователя.
     */
    @Test(dataProvider = "failureLoginData", dataProviderClass = DataProviders.class)
    @Parameters({"email", "password"})
    public void loginFailureUser(String email, String password) {
        installSpec(responseSpec400());
        RegistrationDto registrationDto = new RegistrationDto(email, password);
        given()
                .spec(requestSpec())
                .when()
                .post("api/login")
                .then()
                .log().body()
                .spec(responseSpec400())
                .extract().response();
        Assertions.assertRegistrationFailure(registrationDto);
    }

    /**
     * Тест для проверки, что операция LIST <RESOURCE> возвращает данные отсортированные по годам.
     * Проверяется, что список годов отсортирован.
     */
    @Test
    public void testOnAYear() {
        installSpec(responseSpec200());
        ListDto response = given()
                .spec(requestSpec())
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

