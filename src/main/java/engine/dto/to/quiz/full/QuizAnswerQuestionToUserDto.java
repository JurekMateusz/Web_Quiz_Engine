package engine.dto.to.quiz.full;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizAnswerQuestionToUserDto {
  private long id;
  private String answer;
}
