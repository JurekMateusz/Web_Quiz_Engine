package engine.todo;

import engine.entities.Quiz;

public class QuizToSend {
    private String title;
    private String text;
    private String[] options;

    public QuizToSend() {
    }

    public QuizToSend(Quiz quiz) {
        this.title = quiz.getTitle();
        this.text = quiz.getText();
        this.options = quiz.getOptions();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}