import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserLoginTest {

    private UserClient userClient;
    private User user;
    private Steps step;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getUserData();
        step = new Steps();
        ValidatableResponse userCreateResponse = userClient.create(user);
        int userCreateResponseStatusCode = step.getStatusCode(userCreateResponse);
        step.compareResponseStatusCodeWith200(userCreateResponseStatusCode);
        accessToken = step.getAccessToken(userCreateResponse);
    }

    @After
    public void cleanUp() {
        ValidatableResponse userDeleteResponse = userClient.delete(accessToken);
        step.checkSuccessIsTrue(userDeleteResponse);
    }

    @Test
    @DisplayName("Check successful login")
    @Description("Test checks successful login with correct credentials")
    public void login() {
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));
        int loginStatusCode = step.getStatusCode(loginResponse);
        step.compareResponseStatusCodeWith200(loginStatusCode);
        step.checkSuccessIsTrue(loginResponse);
        step.checkAccessTokenIsNotNull(loginResponse);
        step.checkRefreshTokenIsNotNull(loginResponse);
        step.checkName(loginResponse, user);
        step.checkEmail(loginResponse, user);
    }

    @Test
    @DisplayName("Check unsuccessful login")
    @Description("Test checks that unsuccessful login with wrong email returns the error")
    public void loginWithWrongEmail() {
        user.setEmail("wrongEmail@yandex.ru");
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));
        int loginStatusCode = step.getStatusCode(loginResponse);
        step.compareResponseStatusCodeWith401(loginStatusCode);
        step.checkSuccessIsFalse(loginResponse);
        String message = step.getResponseMessage(loginResponse);
        step.compareResponseMessageWithExpectedFor401(message);
    }

    @Test
    @DisplayName("Check unsuccessful login")
    @Description("Test checks that unsuccessful login with wrong password returns the error")
    public void loginWithWrongPassword() {
        user.setPassword("wrongPassword");
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));
        int loginStatusCode = step.getStatusCode(loginResponse);
        step.compareResponseStatusCodeWith401(loginStatusCode);
        step.checkSuccessIsFalse(loginResponse);
        String message = step.getResponseMessage(loginResponse);
        step.compareResponseMessageWithExpectedFor401(message);
    }

    @Test
    @DisplayName("Check unsuccessful login")
    @Description("Test checks that unsuccessful login with empty email returns the error")
    public void loginWithoutEmail() {
        user.setEmail("");
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));
        int loginStatusCode = step.getStatusCode(loginResponse);
        step.compareResponseStatusCodeWith401(loginStatusCode);
        step.checkSuccessIsFalse(loginResponse);
        String message = step.getResponseMessage(loginResponse);
        step.compareResponseMessageWithExpectedFor401(message);
    }

    @Test
    @DisplayName("Check unsuccessful login")
    @Description("Test checks that unsuccessful login with empty password returns the error")
    public void loginWithoutPassword() {
        user.setPassword("");
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));
        int loginStatusCode = step.getStatusCode(loginResponse);
        step.compareResponseStatusCodeWith401(loginStatusCode);
        step.checkSuccessIsFalse(loginResponse);
        String message = step.getResponseMessage(loginResponse);
        step.compareResponseMessageWithExpectedFor401(message);
    }
}
