package engine.dto.from.quiz.answer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAnswer {
  private long quizQuestionId;
  private List<Long> answer;
}
