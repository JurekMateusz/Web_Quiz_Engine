package engine.entity.user;

import engine.entity.complete.CompleteQuizInfo;
import lombok.*;

import javax.persistence.*;
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

  private String email;
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<CompleteQuizInfo> completeQuiz;
}
