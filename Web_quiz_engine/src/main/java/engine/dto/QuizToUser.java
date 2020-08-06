package engine.dto;

import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizOptions;
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
        this.text = quiz.getQuestion();
        this.options = quiz.getOptions().stream()
                .map(QuizOptions::getContent)
                .toArray(String[]::new);
        this.id = quiz.getId();
    }
}