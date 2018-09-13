package backend.forms;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class UserFormData {
    private String email;
    private String password;
    private String name;
    private String surname;
}
