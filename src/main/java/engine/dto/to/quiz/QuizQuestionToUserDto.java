package engine.dto.to.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuizQuestionToUserDto {
  private long id;
  private String question;
  private List<QuizAnswerQuestionToUserDto> quizAnswerQuestions;
}
