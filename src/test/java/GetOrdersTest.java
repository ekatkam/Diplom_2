import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

public class GetOrdersTest {

    private OrderClient orderClient;
    private Steps step;
    private UserClient userClient;
    private User user;
    private Order order;

   @Before
    public void setUp() {
        orderClient = new OrderClient();
        step = new Steps();
    }

    @Test
    @DisplayName("Check order list with authorization")
    @Description("Test checks getting list of orders for authorized user")
    public void checkOrderListAuth() {
       order = OrderGenerator.getOrderWith2Ingredients();
       userClient = new UserClient();
       user = UserGenerator.getUserData();
       ValidatableResponse userCreateResponse = userClient.create(user);
       int userCreateResponseStatusCode = step.getStatusCode(userCreateResponse);
       step.compareResponseStatusCodeWith200(userCreateResponseStatusCode);

       String accessToken = step.getAccessToken(userCreateResponse);
       ValidatableResponse orderCreateResponse = orderClient.createOrderAuth(order, accessToken);
       int orderCreateResponseStatusCode = step.getStatusCode(orderCreateResponse);
       step.compareResponseStatusCodeWith200(orderCreateResponseStatusCode);

       ValidatableResponse orderListResponse = orderClient.getOrderlist(accessToken);
       int orderListResponseStatusCode = step.getStatusCode(orderListResponse);
       step.compareResponseStatusCodeWith200(orderListResponseStatusCode);
       step.checkSuccessIsTrue(orderListResponse);
       step.checkOrderList(orderListResponse);

       ValidatableResponse userDeleteResponse = userClient.delete(accessToken);
       step.checkSuccessIsTrue(userDeleteResponse);
    }

    @Test
    @DisplayName("Check order list without authorization")
    @Description("Test checks getting list of orders for unauthorized user")
    public void checkOrderListUnauth() {
       ValidatableResponse orderListResponse = orderClient.getOrderListUnauth();
       int orderListResponseStatusCode = step.getStatusCode(orderListResponse);
       step.compareResponseStatusCodeWith401(orderListResponseStatusCode);
       String message = step.getResponseMessage(orderListResponse);
       step.compareResponseMessageWithExpectedFor401Unauth(message);
       step.checkSuccessIsFalse(orderListResponse);

    }
}
