package engine.dto.to.feedback;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnswerFeedback {
  @Getter
  private static final AnswerFeedback SUCCESS =
      new AnswerFeedback(true, "Congratulations, you're right!");

  @Getter
  private static final AnswerFeedback FAILURE =
      new AnswerFeedback(false, "Wrong answer! Please, try again.");

  private final boolean success;
  private final String feedback;
}
