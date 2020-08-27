package engine.dto.converter;

import com.google.common.collect.ImmutableMap;
import com.sdp.common.assemblers.AssemblerFactory;
import engine.dto.converter.answerdto.UserAnswerDtoAssemblerFactory;
import engine.dto.converter.quiz.completeinfo.CompleteQuizInfoAssemblerFactory;
import engine.dto.converter.quiz.header.QuizHeaderDtoAssemblerFactory;
import engine.dto.converter.quiz.question.QuizQuestionAssemblerFactory;
import engine.dto.converter.quiz.quizdto.AddQuizDtoAssemblerFactory;
import engine.dto.converter.user.UserDtoToUserConverter;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.from.quiz.answer.UserAnswerDto;
import engine.dto.from.user.AuthUserDto;
import engine.dto.to.feedback.FeedbackForSolvedQuiz;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizQuestion;

public class AssemblerWebQuizFactory {
  private static final ImmutableMap<Class<?>, AssemblerFactory> map;

  static {
    map =
        ImmutableMap.<Class<?>, AssemblerFactory>builder()
            .put(AddQuizDto.class, new AddQuizDtoAssemblerFactory())
            .put(AuthUserDto.class, new UserDtoToUserConverter())
            .put(Quiz.class, new QuizHeaderDtoAssemblerFactory())
            .put(UserAnswerDto.class, new UserAnswerDtoAssemblerFactory())
            .put(QuizQuestion.class, new QuizQuestionAssemblerFactory())
            .put(FeedbackForSolvedQuiz.class, new CompleteQuizInfoAssemblerFactory())
            .build();
  }

  public static AssemblerFactory getConverter(Class<?> myClass) {
    AssemblerFactory result = map.getOrDefault(myClass, null);
    if (result == null) {
      throw new RuntimeException("Converter dont exist: " + myClass.getName());
    }
    return result;
  }
}
