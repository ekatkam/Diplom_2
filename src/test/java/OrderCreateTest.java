import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

public class OrderCreateTest {

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
    public void orderCreateUnauth() {
        order = OrderGenerator.getOrderWith2Ingredients();
        ValidatableResponse orderCreateResponse = orderClient.createOrder(order);
        int orderCreateResponseStatusCode = step.getStatusCode(orderCreateResponse);
        step.compareResponseStatusCodeWith200(orderCreateResponseStatusCode);
        step.checkOrderName(orderCreateResponse);
        step.checkOrderNumber(orderCreateResponse);
        step.checkSuccessIsTrue(orderCreateResponse);
    }

    @Test
    public void orderCreateAuth() {
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
        step.checkOrderName(orderCreateResponse);
        step.checkOrderNumber(orderCreateResponse);
        step.checkSuccessIsTrue(orderCreateResponse);

        ValidatableResponse userDeleteResponse = userClient.delete(accessToken);
        step.checkSuccessIsTrue(userDeleteResponse);
    }

    @Test
    public void orderCreateWithNoIngredients() {
        order = OrderGenerator.getOrderWithNoIngredients();
        ValidatableResponse orderCreateResponse = orderClient.createOrder(order);
        int orderCreateResponseStatusCode = step.getStatusCode(orderCreateResponse);
        step.compareResponseStatusCodeWith400(orderCreateResponseStatusCode);
        step.checkSuccessIsFalse(orderCreateResponse);
        String message = step.getResponseMessage(orderCreateResponse);
        step.compareResponseMessageWithExpectedFor400(message);
    }

    @Test
    public void orderCreateWithWrongIngredientHash() {
        order = OrderGenerator.getOrderWithWrongIngredientHash();
        ValidatableResponse orderCreateResponse = orderClient.createOrder(order);
        int orderCreateResponseStatusCode = step.getStatusCode(orderCreateResponse);
        step.compareResponseStatusCodeWith500(orderCreateResponseStatusCode);
    }
}
