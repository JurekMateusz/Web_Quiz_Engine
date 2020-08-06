package engine.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    @NotNull
    @Pattern(regexp = ".+@.+\\..+", message = "Invalid email format")
    private String email;
    @NotNull
    @Size(min = 5)
    private String password;
}
