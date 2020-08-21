package engine.dto.from.quiz.add;

import engine.dto.from.quiz.add.AddQuizAnswerQuestionDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddQuizQuestionDto {
    @NotEmpty(message = "Quiz question field can't be empty")
    private String question;

    @Size(min = 2, message = "Min size of answers to chose is 2")
    private List<AddQuizAnswerQuestionDto> quizAnswerQuestions;
}
