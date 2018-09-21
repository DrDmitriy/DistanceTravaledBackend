package backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "eventList")
@Entity
@Table(name = "Users", schema = "public")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name="user_id", nullable = false, unique = true)
    private Long userID;

    @Basic
    @Column(nullable = false, unique = true)
    private String email;

    @Basic
    @Column(nullable = false)
    private String password;

    @Basic
    @Column(nullable = false)
    private String name;

    @Basic
    @Column(nullable = false)
    private String surname;

    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Basic
    @Column(nullable = false)
    private boolean facebook;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntity")
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Event> eventList;
}
