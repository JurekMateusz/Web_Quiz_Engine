package engine.entity.user;

import engine.entity.complete.CompleteQuizInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User implements Serializable {
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

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<CompleteQuizInfo> completeQuiz;
}
