import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Steps {

    @Step("Get response status code")
    public int getStatusCode(ValidatableResponse response) {
        int responseStatusCode = response.extract().statusCode();
        return responseStatusCode;
    }

    @Step("Compare response status code with successful status code")
    public void compareResponseStatusCodeWith200(int responseStatusCode) {
        assertEquals(200,responseStatusCode);
    }

    @Step("Compare response status code with expected")
    public void compareResponseStatusCodeWith403(int responseStatusCode) {
        assertEquals(403,responseStatusCode);
    }

    @Step("Compare response status code with expected")
    public void compareResponseStatusCodeWith401(int responseStatusCode) {
        assertEquals(401,responseStatusCode);
    }

    @Step("Compare response status code with expected")
    public void compareResponseStatusCodeWith400(int responseStatusCode) {
        assertEquals(400,responseStatusCode);
    }

    @Step("Compare response status code with expected")
    public void compareResponseStatusCodeWith500(int responseStatusCode) {
        assertEquals(500,responseStatusCode);
    }

    @Step("Check that success == true")
    public void checkSuccessIsTrue(ValidatableResponse response) {
        boolean success = response.extract().path("success");
        assertTrue(success);
    }

    @Step("Check that success == false")
    public void checkSuccessIsFalse(ValidatableResponse response) {
        boolean success = response.extract().path("success");
        assertFalse(success);
    }

    @Step("Check email")
    public void checkEmail(ValidatableResponse response, User user) {
        String email = response.extract().path("user.email");
        assertEquals(user.getEmail(), email);
    }

    @Step("Check name")
    public void checkName(ValidatableResponse response, User user) {
        String name = response.extract().path("user.name");
        assertEquals(user.getName(), name);
    }

    @Step("Check that access token has value")
    public void checkAccessTokenIsNotNull(ValidatableResponse response) {
        String accessToken = response.extract().path("accessToken");
        assertNotNull(accessToken);
    }

    @Step("Get access token")
    public String getAccessToken(ValidatableResponse response) {
        String accessToken = response.extract().path("accessToken");
        return accessToken;
    }

    @Step("Check that refresh token has value")
    public void checkRefreshTokenIsNotNull(ValidatableResponse response) {
        String refreshToken = response.extract().path("refreshToken");
        assertNotNull(refreshToken);
    }

    @Step("Extract response message")
    public String getResponseMessage(ValidatableResponse response) {
        String message = response.extract().path("message");
        return message;
    }

    @Step("Compare message with expected")
    public void compareResponseMessageWithExpectedFor403SameUser(String message) {
        assertEquals("User already exists", message);
    }

    @Step("Compare message with expected")
    public void compareResponseMessageWithExpectedFor403NoData(String message) {
        assertEquals("Email, password and name are required fields", message);
    }

    @Step("Compare message with expected")
    public void compareResponseMessageWithExpectedFor403SameEmail(String message) {
        assertEquals("User with such email already exists", message);
    }

    @Step("Compare message with expected")
    public void compareResponseMessageWithExpectedFor401(String message) {
        assertEquals("email or password are incorrect", message);
    }

    @Step("Compare message with expected")
    public void compareResponseMessageWithExpectedFor401Unauth(String message) {
        assertEquals("You should be authorised", message);
    }

    @Step("Compare message with expected")
    public void compareResponseMessageWithExpectedFor400(String message) {
        assertEquals("Ingredient ids must be provided", message);
    }

    @Step("Check that order name has a value")
    public void checkOrderName(ValidatableResponse response) {
        String name = response.extract().path("name");
        assertNotNull(name);
    }

    @Step("Check that order number has a value")
    public void checkOrderNumber(ValidatableResponse response) {
        int number = response.extract().path("order.number");
        assertNotEquals(0, number);
    }

    @Step("Check that list of orders is not empty")
    public void checkOrderList(ValidatableResponse response) {
        ArrayList<String> orders = response.extract().path("orders");
        assertNotEquals(0, orders.size());
    }

}
