package engine.dto.to.feedback;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FeedbackAnswerForSingleQuiz {
  @Getter
  private static final FeedbackAnswerForSingleQuiz SUCCESS =
      new FeedbackAnswerForSingleQuiz(true, "Congratulations, you're right!");

  @Getter
  private static final FeedbackAnswerForSingleQuiz FAILURE =
      new FeedbackAnswerForSingleQuiz(false, "Wrong answer! Please, try again.");

  private final boolean success;
  private final String feedback;
}
