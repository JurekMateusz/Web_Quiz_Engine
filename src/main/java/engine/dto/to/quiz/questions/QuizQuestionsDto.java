package engine.dto.to.quiz.questions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizQuestionsDto {
  private long questionId;
  private long quizId;

  private String question;
}
