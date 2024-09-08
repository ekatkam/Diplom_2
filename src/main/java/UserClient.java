import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {

    private static final String USER_REGISTER_PATH = "api/auth/register";
    private static final String USER_PATH = "api/auth/user";
    private static final String LOGIN_PATH = "api/auth/login";

    @Step("Create user")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_REGISTER_PATH)
                .then();
    }

    @Step("Delete user")
    public ValidatableResponse delete(String token) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .delete(USER_PATH)
                .then();
    }

    @Step("Login")
    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Update user")
    public ValidatableResponse update(User user, String token) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .body(user)
                .when()
                .patch(USER_PATH)
                .then();
    }

    @Step("Update  (unauthorized)")
    public ValidatableResponse updateUnauth(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(USER_PATH)
                .then();
    }


}
