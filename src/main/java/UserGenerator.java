public class UserGenerator {

    public static User getUserData() {
        User user = new User();
        user.setEmail("test28081818@yandex.ru");
        user.setName("Username");
        user.setPassword("password");
        return user;
    }

    public static User getExtraUserData() {
        User extraUser = new User();
        extraUser.setEmail("test29081646@yandex.ru");
        extraUser.setName("Username");
        extraUser.setPassword("password");
        return extraUser;
    }
}
