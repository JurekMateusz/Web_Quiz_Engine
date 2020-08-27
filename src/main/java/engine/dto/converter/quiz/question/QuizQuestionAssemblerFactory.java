package engine.dto.converter.quiz.question;

import com.sdp.common.assemblers.Assembler;
import com.sdp.common.assemblers.AssemblerFactory;
import com.sdp.common.assemblers.StandardAssemblerBuilder;
import engine.dto.to.quiz.questions.QuizQuestionsDto;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizQuestion;

import javax.validation.constraints.NotNull;

public class QuizQuestionAssemblerFactory extends AssemblerFactory<QuizQuestion, QuizQuestionsDto> {
  @Override
  protected @NotNull Assembler<QuizQuestion, QuizQuestionsDto> createAssemblerFactory() {
    return StandardAssemblerBuilder.create(QuizQuestion.class, QuizQuestionsDto.class)
        .from(QuizQuestion::getId)
        .to(QuizQuestionsDto::setQuestionId)
        .from(QuizQuestion::getQuestion)
        .to(QuizQuestionsDto::setQuestion)
        .from(QuizQuestion::getQuiz)
        .mapWith(Quiz::getId)
        .to(QuizQuestionsDto::setQuizId)
        .build();
  }
}
