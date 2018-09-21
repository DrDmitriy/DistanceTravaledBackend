package backend.forms;

import lombok.*;

@NoArgsConstructor
//@AllArgsConstructor
@Data
@Builder
@ToString
public class UserFormData {
    private String email;
    private String password;
    private String name;
    private String surname;

    public UserFormData(String email, String password, String name, String surname){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}
