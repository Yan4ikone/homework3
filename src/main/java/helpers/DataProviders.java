package helpers;

import org.testng.annotations.DataProvider;

public class DataProviders {

    public static final Object[][] SUCCESS_LOGIN_DATA = {{"eve.holt@reqres.in","cityslicka"}};
    public static final Object[][] FAILURE_LOGIN_DATA = {{"sydney@fife", null}};
    public static final Object[][] FAILURE_LOGIN_RESPONSE = {{"token", "QpwL5tke4Pnpja7X4"}};


    @DataProvider(name = "successLoginData")
    public static Object[][] successLoginData() {
        return SUCCESS_LOGIN_DATA;
    }

    @DataProvider(name = "failureLoginData")
    public static Object[][] failureLoginData() {
        return FAILURE_LOGIN_DATA;
    }

    @DataProvider(name = "failureLoginResponse")
    public static Object[][] failureLoginResponse() {
        return FAILURE_LOGIN_RESPONSE;
    }



}

