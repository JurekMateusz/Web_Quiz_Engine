package engine.entity.complete;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CompleteQuizInfo implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "quiz_id", updatable = false, nullable = false)
  private long quizId;

  @Column(name = "user_id")
  private long userId;

  private LocalDateTime startedAt;
  private LocalDateTime completedAt;
  private int numberOfQuestions;
  private int numberOfAnswers;
  private int correctAnswers;
  private int wrongAnswers;
}
