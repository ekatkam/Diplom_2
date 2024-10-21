import io.restassured.response.ValidatableResponse;

public class OrderGenerator {

    private static IngredientsClient ingredientsClient = new IngredientsClient();
    private static ValidatableResponse getIngredientResponse = ingredientsClient.getIngredientList();
    private static String ingredient1 = getIngredientResponse.extract().body().path("data[0]._id");
    private static String ingredient2 = getIngredientResponse.extract().body().path("data[1]._id");

    public static Order getOrderWith2Ingredients() {
        Order order = new Order(new String[]{ingredient1, ingredient2});
        return order;
    }

    public static Order getOrderWithNoIngredients() {
        Order order = new Order(new String[]{});
        return order;
    }

    public static Order getOrderWithWrongIngredientHash() {
        Order order = new Order(new String[]{"wrong_hash"});
        return order;
    }
}
