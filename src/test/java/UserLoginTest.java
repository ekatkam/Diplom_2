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
