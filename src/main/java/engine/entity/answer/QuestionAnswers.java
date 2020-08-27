package engine.entity.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Morph;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "question_answers")
public class QuestionAnswers {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "question_answers_id")
  private long id;

  private long quizId;
  private long quizQuestionId;
  private long userId;

  @OneToMany(
      mappedBy = "questionAnswers",orphanRemoval = true,
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
  private List<Answer> answers;
}
