package in.reqres;

import data.*;
import helpers.Assertions;
import helpers.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
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

public class TestsApi {

    @Test
    public void testOne() {
        installSpec(requestSpec(), responseSpec200());
        UserResponseDto userRes = given()
                .spec(requestSpec())
                .when()
                .get("api/users?page=2")
                .then()
                .log().body()
                .body("page", notNullValue())
                .spec(responseSpec200())
                .extract().body().as(UserResponseDto.class);
        List<String> avatarUrl = userRes.getData().stream()
                .map(UserDto::getAvatar)
                .toList();
        Set<String> uniqueUrl = userRes.getData().stream()
                .map(UserDto::getAvatar)
                .collect(Collectors.toSet());
        Assert.assertEquals(avatarUrl.size(), uniqueUrl.size(),
                STR."Список не уникален, ждали: \{avatarUrl} получили: \{uniqueUrl}");
        deleteSpec();
    }

    @Test(dataProvider = "successLoginData", dataProviderClass = DataProviders.class)
    @Parameters({"email", "password"})
    public void registerUser(String email, String password) {
        installSpec(requestSpec(), responseSpec201());
        RegistrationDto registrationDto = new RegistrationDto(email, password);
        given()
                .spec(requestSpec())
                .when()
                .post("api/users")
                .then()
                .log().body()
                .spec(responseSpec201())
                .extract().response();
        Assertions.assertRegistrationSuccess(email, password, registrationDto);
        deleteSpec();
    }

    @Test(dataProvider = "failureLoginData", dataProviderClass = DataProviders.class)
    @Parameters({"email", "password"})
    public void registerFailureUser(String email, String password) {
        installSpec(requestSpec(), responseSpec201());
        RegistrationDto registrationDto = new RegistrationDto(email, password);
        given()
                .spec(requestSpec())
                .when()
                .post("api/users")
                .then()
                .log().body()
                .spec(responseSpec201())
                .extract().response();
        Assertions.assertRegistrationFailure(registrationDto);
        deleteSpec();
    }

    @Test
    public void testOnAYear() {
        installSpec(requestSpec(), responseSpec200());
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
        deleteSpec();
    }

    @Test
    public void testGateway() {
        installSpec(requestSpec(), responseSpec200());
        Response response = given()
                .when()
                .get("https://gateway.autodns.com/")
                .then()
                .log().all()
                .spec(responseSpec200())
                .extract().response();
        // Получаем тело ответа в виде строки
        String responseBody = response.asString();

        // Регулярное выражение для поиска всех открывающих тегов (но не закрывающих)
        Pattern pattern = Pattern.compile("<(\\?*)(\\w+)(\\s|>)"); // Ищем <тег без /
        Matcher matcher = pattern.matcher(responseBody);

        int tagCount = 0;
        while (matcher.find()) {
            tagCount++;
        }

        System.out.println("Количество открывающих тегов: " + tagCount);
    }
}

