package engine.dto.to.quiz.full;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuizAnswerQuestionDto {
  private long id;
  private String answer;

  public static QuizAnswerQuestionDto of(long id, String answer) {
    return new QuizAnswerQuestionDto(id, answer);
  }
}
