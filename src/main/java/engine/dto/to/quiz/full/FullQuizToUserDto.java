package engine.dto.to.quiz.full;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class FullQuizToUserDto {
  private long id;
  private String title;
  private List<QuizQuestionToUserDto> quizQuestions;

  public static FullQuizToUserDto of(
      long id, String title, List<QuizQuestionToUserDto> quizQuestions) {
    return new FullQuizToUserDto(id, title, quizQuestions);
  }
}
