import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ORDER_PATH = "api/orders";

    @Step("Create an order (unauthorized)")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Create an order (authorized)")
    public ValidatableResponse createOrderAuth(Order order, String token) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Get order list (authorized)")
    public ValidatableResponse getOrderlist(String token) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .get(ORDER_PATH)
                .then();
    }

    @Step("Get order list (unauthorized)")
    public ValidatableResponse getOrderListUnauth() {
        return given()
                .spec(getBaseSpec())
                .get(ORDER_PATH)
                .then();
    }

}
