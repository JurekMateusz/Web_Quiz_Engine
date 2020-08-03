package engine.entity.quiz;

import engine.dto.QuizFromUser;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quiz_id")
    private long id;

    @NonNull
    private String title;

    @NonNull
    private String question;

    @NonNull
    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<QuizOptions> options;

    @NonNull
    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<QuizAnswers> answer;

    public Quiz(QuizFromUser quizFromUser) {
        this.title = quizFromUser.getTitle();
        this.question = quizFromUser.getText();
        this.options = quizFromUser.getOptions().stream()
                .map(e -> QuizOptions.builder()
                        .content(e)
                        .quiz(this)
                        .build())
                .collect(Collectors.toList());
        this.answer = quizFromUser.getAnswer().stream()
                .map(e -> QuizAnswers.builder()
                        .answer(e)
                        .quiz(this)
                        .build())
                .collect(Collectors.toList());
    }
}