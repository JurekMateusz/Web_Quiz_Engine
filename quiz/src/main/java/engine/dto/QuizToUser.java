package engine.dto;

import engine.entity.Quiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizToUser {
    private long id;
    private String title;
    private String text;
    private String[] options;

    public QuizToUser(Quiz quiz) {
        this.title = quiz.getTitle();
        this.text = quiz.getText();
        this.options = quiz.getOptions();
        this.id = quiz.getId();
    }
}