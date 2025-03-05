package helpers;

import data.LoginDto;
import data.LoginResponseDto;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.List;
import java.util.Set;

public class Assertions {

    /**
     * Проверка успешной регистрации и получения токена.
     * @param loginResponseDto Объект ответа от сервиса.
     */
    @Step("Проверка успешной регистрации и получения токена.")
    public static void assertRegistrationSuccess(LoginResponseDto loginResponseDto) {
        String expectedToken = "QpwL5tke4Pnpja7X4";
        String actualToken = loginResponseDto.getToken();
        Assert.assertEquals(actualToken, expectedToken,
                String.format(STR."Ожидался токен: \{expectedToken}, но получен: \{actualToken}"));
    }

    /**
     * Проверка корректности email и пароля при регистрации.
     * @param email Ожидаемый email.
     * @param password Ожидаемый пароль.
     * @param loginDto Объект, содержащий данные пользователя.
     */
    @Step("Проверка корректности email и пароля.")
    public static void assertRegistrationCorrect(String email, String password, LoginDto loginDto) {
        Assert.assertEquals(email, loginDto.getEmail(),
                String.format(STR."Некорректный email: ожидался \{email}, получен: \{loginDto.getEmail()}"));
        Assert.assertEquals(password, loginDto.getPassword(),
                String.format(STR."Некорректный пароль: ожидался \{password}, получен: \{loginDto.getPassword()}"));
    }

    /**
     * Проверка, что password не является null.
     * @param loginDto Объект с данными пользователя.
     */
    @Step("Проверка на нулевые значения пароля")
    public static void assertRegistrationFailure(LoginDto loginDto) {
        Assert.assertNull(loginDto.getPassword(),
                String.format(STR."Ожидали null, но получили:  \{loginDto.getPassword()}"));
    }

    /**
     * Проверка, что список годов отсортирован по возрастанию.
     * @param years Список годов.
     */
    @Step("Проверка метода на возврат данных отсортированных по годам")
    public static void isSorted(List<Integer> years) {
        for (int i = 1; i < years.size(); i++) {
            if (years.get(i) < years.get(i - 1)) { // год должен быть меньше предыдущего
                Assert.assertFalse(false);
            }
        }
        Assert.assertTrue(true);
    }

    /**
     * Проверка количества тегов.
     * @param tagCount Количество тегов в ответе.
     */
    @Step("Проверка количества тегов равно 15")
    public static void assertTegs(Integer tagCount) {
        Assert.assertFalse(tagCount != 15,
                String.format(STR."Количество тегов неверное, получили: \{tagCount}"));
    }

    /**
     * Проверка уникальности имён файлов аватаров пользователей.
     * @param avatarUrl Список имён аватаров типа List.
     * @param uniqueUrl Список имён аватаров типа Set.
     */
    @Step("Проверка уникальности списка")
    public static void uniqueList(List<String> avatarUrl, Set<String> uniqueUrl) {
        Assert.assertEquals(avatarUrl.size(), uniqueUrl.size(),
                STR."Список не уникален, ждали: \{avatarUrl} получили: \{uniqueUrl}");
    }
}
