package engine.dto.from.quiz.add;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class AddQuizDto {
  @NotEmpty(message = "Quiz title can't be empty")
  private String title;

  @NotNull private List<AddQuizQuestionDto> quizQuestions;
}
