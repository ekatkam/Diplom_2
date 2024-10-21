import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestClient {

    private static final String INGREDIENTS_PATH = "api/ingredients";

    @Step("Get ingredient list")
    public ValidatableResponse getIngredientList() {
        return given()
                .spec(getBaseSpec())
                .get(INGREDIENTS_PATH)
                .then();
    }
}
