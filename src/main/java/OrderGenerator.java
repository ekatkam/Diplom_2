public class OrderGenerator {

    public static Order getOrderWith2Ingredients() {
        Order order = new Order(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"});
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
