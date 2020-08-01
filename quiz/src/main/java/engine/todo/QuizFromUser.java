package engine.todo;

import engine.entity.Quiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizFromUser {
    private String title;
    private String text;
    private String[] options;
    private int answer;

    public QuizFromUser(Quiz quiz) {
        this.title = quiz.getTitle();
        this.text = quiz.getText();
        this.options = quiz.getOptions();
    }
}

