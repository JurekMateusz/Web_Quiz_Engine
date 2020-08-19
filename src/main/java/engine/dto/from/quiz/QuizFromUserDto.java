package engine.dto.from.quiz;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizFromUserDto {
  @NotEmpty(message = "Quiz title can't be empty")
  private String title;

  @NotNull
  @NotEmpty(message = "Quiz text field can't be empty")
  private String text;

  @NotNull
  @Size(min = 2, message = "Min size of options to chose is 2")
  private List<String> options;

  private List<Integer> answer = new ArrayList<>();
}
