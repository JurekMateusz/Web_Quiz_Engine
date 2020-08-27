package engine.dto.from.quiz.add;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddQuizAnswerQuestionDto {
  @NotNull @NotEmpty private String answer;
  @NotNull private boolean correct;
}
