import com.github.javafaker.Faker;

public class UserGenerator {

    public static User getUserData() {
        Faker faker = new Faker();
        return new User(faker.internet().emailAddress(),
                faker.internet().password(),
                faker.name().firstName());
    }

    public static User getExtraUserData() {
        User extraUser = new User();
        extraUser.setEmail("sameemailtest@yandex.ru");
        extraUser.setName("Username");
        extraUser.setPassword("password");
        return extraUser;
    }
}
