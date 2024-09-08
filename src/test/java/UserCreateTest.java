import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

public class UserCreateTest {

    private UserClient userClient;
    private User user;
    private Steps step;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getUserData();
        step = new Steps();
    }

    @Test
    public void userCreate() {
        ValidatableResponse userCreateResponse = userClient.create(user);
        int userCreateResponseStatusCode = step.getStatusCode(userCreateResponse);
        step.compareResponseStatusCodeWith200(userCreateResponseStatusCode);
        step.checkSuccessIsTrue(userCreateResponse);
        step.checkEmail(userCreateResponse, user);
        step.checkName(userCreateResponse, user);
        step.checkAccessTokenIsNotNull(userCreateResponse);
        step.checkRefreshTokenIsNotNull(userCreateResponse);
        String accessToken = step.getAccessToken(userCreateResponse);
        ValidatableResponse userDeleteResponse = userClient.delete(accessToken);
        step.checkSuccessIsTrue(userDeleteResponse);
    }

    @Test
    public void createSameUser() {
        ValidatableResponse userCreateResponse = userClient.create(user);
        String accessToken = step.getAccessToken(userCreateResponse);
        ValidatableResponse sameUserCreateResponse = userClient.create(user);
        int createSameUserResponseStatusCode = step.getStatusCode(sameUserCreateResponse);
        step.compareResponseStatusCodeWith403(createSameUserResponseStatusCode);
        step.checkSuccessIsFalse(sameUserCreateResponse);
        String sameUserResponseMessage = step.getResponseMessage(sameUserCreateResponse);
        step.compareResponseMessageWithExpectedFor403SameUser(sameUserResponseMessage);
        ValidatableResponse userDeleteResponse = userClient.delete(accessToken);
        step.checkSuccessIsTrue(userDeleteResponse);
    }

    @Test
    public void createUserWithoutEmail() {
        user.setEmail("");
        ValidatableResponse userCreateResponse = userClient.create(user);
        int userCreateResponseStatusCode = step.getStatusCode(userCreateResponse);
        step.compareResponseStatusCodeWith403(userCreateResponseStatusCode);
        step.checkSuccessIsFalse(userCreateResponse);
        String message = step.getResponseMessage(userCreateResponse);
        step.compareResponseMessageWithExpectedFor403NoData(message);
    }

    @Test
    public void createUserWithoutPassword() {
        user.setPassword("");
        ValidatableResponse userCreateResponse = userClient.create(user);
        int userCreateResponseStatusCode = step.getStatusCode(userCreateResponse);
        step.compareResponseStatusCodeWith403(userCreateResponseStatusCode);
        step.checkSuccessIsFalse(userCreateResponse);
        String message = step.getResponseMessage(userCreateResponse);
        step.compareResponseMessageWithExpectedFor403NoData(message);
    }

    @Test
    public void createUserWithoutName() {
        user.setName("");
        ValidatableResponse userCreateResponse = userClient.create(user);
        int userCreateResponseStatusCode = step.getStatusCode(userCreateResponse);
        step.compareResponseStatusCodeWith403(userCreateResponseStatusCode);
        step.checkSuccessIsFalse(userCreateResponse);
        String message = step.getResponseMessage(userCreateResponse);
        step.compareResponseMessageWithExpectedFor403NoData(message);
    }
}
