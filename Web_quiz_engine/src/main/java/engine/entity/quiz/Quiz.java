package engine.entity.quiz;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "quiz_id")
  private long id;

  @NonNull private String title;

  @NonNull private String question;

  @NonNull
  @OneToMany(
      mappedBy = "quiz",
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
  private List<QuizOptions> options;

  @NonNull
  @OneToMany(
      mappedBy = "quiz",
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
  private List<QuizAnswers> answer;

  @Column(name = "user_id", nullable = true)
  private long userId;
}
