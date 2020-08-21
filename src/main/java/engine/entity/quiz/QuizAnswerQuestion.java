package engine.entity.quiz;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuizAnswerQuestion implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "option_id")
  private long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "quiz_question_id")
  private QuizQuestion quizQuestion;

  private String answer;

  @Enumerated(value = EnumType.ORDINAL)
  private AnswerStatus status;
}
