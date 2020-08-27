package engine.dto.to.feedback;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackForSolvedQuiz {
  private LocalDateTime startedAt;
  private LocalDateTime completedAt;
  private int numberOfQuestions;
  private int numberOfAnswers;
  private int correctAnswers;
  private int wrongAnswers;
}
