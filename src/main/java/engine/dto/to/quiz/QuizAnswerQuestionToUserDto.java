package engine.dto.to.quiz;

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
