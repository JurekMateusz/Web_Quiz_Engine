package engine.todo;

public class QuizResult {
    private static final QuizResult SUCCESS = new QuizResult(true, "Congratulations, you're right!");
    private static final QuizResult FAILURE = new QuizResult(false, "Wrong answer! Please, try again.");

    private boolean success;
    private String feedback;

    private QuizResult(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }

    public static QuizResult getSuccess() {
        return SUCCESS;
    }

    public static QuizResult getFailure() {
        return FAILURE;
    }
}