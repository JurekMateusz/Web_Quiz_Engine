package engine.dto.converter.answerdto;

import com.sdp.common.assemblers.Assembler;
import com.sdp.common.assemblers.AssemblerFactory;
import com.sdp.common.assemblers.StandardAssemblerBuilder;
import engine.dto.from.quiz.answer.UserAnswerDto;
import engine.entity.answer.Answer;
import engine.entity.answer.QuestionAnswers;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

public class UserAnswerDtoAssemblerFactory
    extends AssemblerFactory<UserAnswerDto, QuestionAnswers> {
  @Override
  protected @NotNull Assembler<UserAnswerDto, QuestionAnswers> createAssemblerFactory() {
    return StandardAssemblerBuilder.create(UserAnswerDto.class, QuestionAnswers.class)
        .from(UserAnswerDto::getQuizQuestionId)
        .to(QuestionAnswers::setQuizQuestionId)
        .from(UserAnswerDto::getQuizId)
        .to(QuestionAnswers::setQuizId)
        .from(UserAnswerDto::getAnswers)
        .mapWith(
            longs ->
                longs.stream()
                    .map(
                        aLong -> {
                          Answer answer = new Answer();
                          answer.setAnswerId(aLong);
                          return answer;
                        })
                    .collect(Collectors.toList()))
        .to(QuestionAnswers::setAnswers)
        .build();
  }
}
