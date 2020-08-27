package engine.dto.from.quiz.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserAnswerDto {
  @NotNull
  @NotEmpty(message = "Answers can't be empty")
  private long quizQuestionId;

  @NotNull private long quizId;

  @NotNull
  @Size(min = 1, message = "Min size of answers to chose is 1")
  private List<Long> answers;
}
