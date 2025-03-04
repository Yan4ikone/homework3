package helpers;

import data.RegistrationDto;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.List;


public class Assertions {

    @Step("Проверка вводимых значений с получаемыми: '{message}'")
    public static void assertRegistrationSuccess(String expectedEmail, String expectedPassword, RegistrationDto registrationDto) {
        Assert.assertTrue(registrationDto.getEmail().contains(expectedEmail),
                "Ждали: " + expectedEmail + " получили: " + registrationDto.getEmail());
        Assert.assertTrue(registrationDto.getPassword().contains(expectedPassword),
                "Ждали: " + expectedPassword + " получили: " + registrationDto.getPassword());
    }

    @Step("Проверка на нулевые значения: '{message}'")
    public static void assertRegistrationFailure(RegistrationDto registrationDto) {
        Assert.assertNull(registrationDto.getPassword(), "Ожидали null, но получили: " + registrationDto.getPassword());
    }

    @Step("Проверка метода на возврат данных отсортированных по годам:  {condition}")
    public static void isSorted(List<Integer> years) {
        for (int i = 1; i < years.size(); i++) {
            if (years.get(i) < years.get(i - 1)) {
                Assert.assertFalse(false);
            }
        }
        Assert.assertTrue(true);
    }
}