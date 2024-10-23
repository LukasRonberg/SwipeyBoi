package dat.dtos;

import dat.security.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;

    private int age;

    private int phoneNumber;

    private String email;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.age = user.getAge();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }
}
