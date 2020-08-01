package engine.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuizFeedback {
    @Getter
    private static final QuizFeedback SUCCESS = new QuizFeedback(true, "Congratulations, you're right!");
    @Getter
    private static final QuizFeedback FAILURE = new QuizFeedback(false, "Wrong answer! Please, try again.");

    private boolean success;
    private String feedback;
}