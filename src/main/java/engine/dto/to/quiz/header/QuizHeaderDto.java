package engine.dto.to.quiz.header;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizHeaderDto {
  private long id;
  private String title;
  private int numberOfQuestions;
  private long userId;
  private boolean isMade;
}
