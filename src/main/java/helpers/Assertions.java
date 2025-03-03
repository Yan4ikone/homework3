package helpers;

import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import specifications.Specifications;

public class Assertions {
    @Step("Проверяем, что нет ошибки: '{message}'")
    public static void assertTrue(boolean condition, String message) {
        org.junit.jupiter.api.Assertions.assertTrue(condition, message);
    }

  /*  @Step
    public static void assertStatusCode(Specifications specifications) {
        Assert.assertEquals(specifications
                String.format("Expected status code %d but got %d"

    }*/
}
