package engine.todo;

public class QuizFeedback {
    private static final QuizFeedback SUCCESS = new QuizFeedback(true, "Congratulations, you're right!");
    private static final QuizFeedback FAILURE = new QuizFeedback(false, "Wrong answer! Please, try again.");

    private boolean success;
    private String feedback;

    private QuizFeedback(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }

    public static QuizFeedback getSuccess() {
        return SUCCESS;
    }

    public static QuizFeedback getFailure() {
        return FAILURE;
    }
}