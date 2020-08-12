package engine.entity;

import engine.todo.QuizFromUser;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Quiz {
    private long id;
    @NonNull
    private String title;
    @NonNull
    private String text;
    @NonNull
    private String[] options;
    @NonNull
    private int answer;

    public Quiz(QuizFromUser quizFromUser) {
        this.title = quizFromUser.getTitle();
        this.text = quizFromUser.getText();
        this.options = quizFromUser.getOptions();
        this.answer = quizFromUser.getAnswer();
    }
}