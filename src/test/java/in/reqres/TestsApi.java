package in.reqres;

import data.RegistrationDto;
import data.RegistrationResponseDto;
import data.UserDto;
import data.UserResponseDto;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static specifications.Specifications.*;

public class TestsApi {

    @Test
    public void firsTest() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page", notNullValue())
                .body("data.id", not(hasItem(nullValue())))
                .body("data.first_name", hasItem("Lindsay"))
                .statusCode(200);
    }

    @Test
    public void secondTest() {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("name", "Yan");
        requestData.put("job", "stager");
        Response response = given()
                .contentType("application/json")
                .body(requestData)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .extract().response();
        JsonPath jsonResponse = response.jsonPath();
        Assert.assertEquals(requestData.get("name"), jsonResponse.get("name"), "Ожидали создание пользователя " +
                "с именем : " + requestData.get("name") + ", а создался пользователь с именем: " +
                jsonResponse.get("name"));
        Assert.assertEquals(requestData.get("job"), jsonResponse.get("job"), "Ожидали создание пользователя " +
                "с профессией : " + requestData.get("job") + ", а создался пользователь с профессией: " +
                jsonResponse.get("job"));
    }



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
                        .collect(Collectors.toList());
        Set<String> uniqueUrl = userRes.getData().stream()
                        .map(UserDto::getAvatar)
                        .collect(Collectors.toSet());
        Assert.assertEquals(avatarUrl.size(), uniqueUrl.size(),
                "Список не уникален, ждали: " + avatarUrl + " получили: " + uniqueUrl);
        deleteSpec();
    }

    @Test
    @Parameters({"email", "password"})
    public void registerUser(String email, String password, Integer id, String token) {
        Map<String, String> requestData = new HashMap<>();
        Response response = given()
                .spec(requestSpec())
                .body(email)
                .body(password)
                .when()
                .post("api/register")
                .then()
                .log().body()
                .spec(responseSpec200())
                .extract().response();
        System.out.println(email);
        System.out.println(password);
        RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto();
    Assert.assertTrue(req);

    }
}
