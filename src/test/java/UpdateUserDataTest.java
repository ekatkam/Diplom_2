import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UpdateUserDataTest {

    private UserClient userClient;
    private User user;
    private User extraUser;
    private Steps step;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getUserData();
        extraUser = UserGenerator.getExtraUserData();
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
    public void updateEmailAuth() {
        user.setEmail("new_email29081327@yandex.ru");
        ValidatableResponse userUpdateResponse = userClient.update(user, accessToken);
        int userUpdateResponseStatusCode = step.getStatusCode(userUpdateResponse);
        step.compareResponseStatusCodeWith200(userUpdateResponseStatusCode);
        step.checkSuccessIsTrue(userUpdateResponse);
        step.checkEmail(userUpdateResponse, user);
        step.checkName(userUpdateResponse, user);
    }

    @Test
    public void updateNameAuth() {
        user.setName("new_name29081336");
        ValidatableResponse userUpdateResponse = userClient.update(user, accessToken);
        int userUpdateResponseStatusCode = step.getStatusCode(userUpdateResponse);
        step.compareResponseStatusCodeWith200(userUpdateResponseStatusCode);
        step.checkSuccessIsTrue(userUpdateResponse);
        step.checkEmail(userUpdateResponse, user);
        step.checkName(userUpdateResponse, user);
    }

    @Test
    public void updatePasswordAuth() {
        user.setPassword("new_password");
        ValidatableResponse userUpdateResponse = userClient.update(user, accessToken);
        int userUpdateResponseStatusCode = step.getStatusCode(userUpdateResponse);
        step.compareResponseStatusCodeWith200(userUpdateResponseStatusCode);
        step.checkSuccessIsTrue(userUpdateResponse);
        step.checkEmail(userUpdateResponse, user);
        step.checkName(userUpdateResponse, user);
    }

    @Test
    public void updateEmailUnauth() {
        user.setEmail("new_email29081327@yandex.ru");
        ValidatableResponse userUpdateResponse = userClient.updateUnauth(user);
        int userUpdateResponseStatusCode = step.getStatusCode(userUpdateResponse);
        step.compareResponseStatusCodeWith401(userUpdateResponseStatusCode);
        step.checkSuccessIsFalse(userUpdateResponse);
        String message = step.getResponseMessage(userUpdateResponse);
        step.compareResponseMessageWithExpectedFor401Unauth(message);
    }

    @Test
    public void updateNameUnauth() {
        user.setName("new_name29081336");
        ValidatableResponse userUpdateResponse = userClient.updateUnauth(user);
        int userUpdateResponseStatusCode = step.getStatusCode(userUpdateResponse);
        step.compareResponseStatusCodeWith401(userUpdateResponseStatusCode);
        step.checkSuccessIsFalse(userUpdateResponse);
        String message = step.getResponseMessage(userUpdateResponse);
        step.compareResponseMessageWithExpectedFor401Unauth(message);
    }

    @Test
    public void updatePasswordUnauth() {
        user.setPassword("new_password");
        ValidatableResponse userUpdateResponse = userClient.updateUnauth(user);
        int userUpdateResponseStatusCode = step.getStatusCode(userUpdateResponse);
        step.compareResponseStatusCodeWith401(userUpdateResponseStatusCode);
        step.checkSuccessIsFalse(userUpdateResponse);
        String message = step.getResponseMessage(userUpdateResponse);
        step.compareResponseMessageWithExpectedFor401Unauth(message);
    }

    @Test
    public void updateEmailSameValue() {
        userClient.create(extraUser);
        user.setEmail(extraUser.getEmail());
        ValidatableResponse userUpdateResponse = userClient.update(user, accessToken);
        int userUpdateResponseStatusCode = step.getStatusCode(userUpdateResponse);
        step.compareResponseStatusCodeWith403(userUpdateResponseStatusCode);
        step.checkSuccessIsFalse(userUpdateResponse);
        String message = step.getResponseMessage(userUpdateResponse);
        step.compareResponseMessageWithExpectedFor403SameEmail(message);
    }
}
