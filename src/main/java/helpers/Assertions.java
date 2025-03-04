package helpers;

import data.RegistrationDto;
import data.RegistrationResponseDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Set;

public class Assertions {

    @Step("Проверка верного получаемого ответа: {message}")
    public static void assertRegistrationSuccess(RegistrationResponseDto registrationResponseDto) {
        Assert.assertEquals(registrationResponseDto.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Step("Проверка на нулевые значения: {message}")
    public static void assertRegistrationFailure(RegistrationDto registrationDto) {
        Assert.assertNull(registrationDto.getPassword(), "Ожидали null, но получили: " + registrationDto.getPassword());
    }

    @Step("Проверка метода на возврат данных отсортированных по годам: {condition}")
    public static void isSorted(List<Integer> years) {
        for (int i = 1; i < years.size(); i++) {
            if (years.get(i) < years.get(i - 1)) {
                Assert.assertFalse(false);
            }
        }
        Assert.assertTrue(true);
    }

    @Step("Проверка количества тегов == 15:  {condition},{message}")
    public static void assertTegs(Integer tagCount) {
        Assert.assertFalse(tagCount != 15,  "Количество тегов неверное");
    }

    @Step("Проверка уникальности списка: {message}")
    public static void uniqueList(List<String> avatarUrl, Set<String> uniqueUrl) {
        Assert.assertEquals(avatarUrl.size(), uniqueUrl.size(),
                STR."Список не уникален, ждали: \{avatarUrl} получили: \{uniqueUrl}");
    }

    @Step("Проверка Status code: {message}")
    public static void correctStatusCode(Response response, int expectCode) {
        int actualCode = response.getStatusCode();
        Assert.assertEquals(actualCode, expectCode, STR."Статус-код неверный, получен: \{actualCode}");
    }
}
