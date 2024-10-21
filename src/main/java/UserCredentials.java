import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCredentials {

    private String email;
    private String password;

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

}
