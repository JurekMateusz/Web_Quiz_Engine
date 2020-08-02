package engine.dto;

import engine.entity.Quiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class QuizFromUser {
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @Size(min = 2)
    @NotEmpty
    private String[] options;
    private int[] answer = new int[0];

    public QuizFromUser(Quiz quiz) {
        this.title = quiz.getTitle();
        this.text = quiz.getText();
        this.options = quiz.getOptions();
    }
}

