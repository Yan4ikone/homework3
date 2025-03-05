package helpers;

import org.testng.annotations.DataProvider;

public class DataProviders {

    public static final Object[][] SUCCESS_LOGIN_DATA = {{"eve.holt@reqres.in","cityslicka"}};
    public static final Object[][] FAILURE_LOGIN_DATA = {{"sydney@fife", null}};

    @DataProvider(name = "successLoginData")
    public static Object[][] successLoginData() {
        return SUCCESS_LOGIN_DATA;
    }

    @DataProvider(name = "failureLoginData")
    public static Object[][] failureLoginData() {
        return FAILURE_LOGIN_DATA;
    }
}

