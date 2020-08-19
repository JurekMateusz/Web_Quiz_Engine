package engine.dto.converter;

import engine.dto.converter.complete.CompleteQuizInfoToDtoImpl;
import engine.dto.converter.quiz.QuizEntityToQuizDtoImpl;
import engine.dto.converter.quizdto.QuizFromUserDtoToQuizEntityImpl;

import engine.dto.converter.user.UserDtoToUserConverter;
import engine.dto.from.quiz.QuizFromUserDto;
import engine.dto.from.user.AuthUserDto;
import engine.entity.complete.CompleteQuizInfo;
import engine.entity.quiz.Quiz;

public class ConverterFactory {
  public static Converter getConverter(Class<?> myClass) {
    if (myClass == QuizFromUserDto.class) {
      return new QuizFromUserDtoToQuizEntityImpl();
    } else if (myClass == Quiz.class) {
      return new QuizEntityToQuizDtoImpl();
    } else if (myClass == CompleteQuizInfo.class) {
      return new CompleteQuizInfoToDtoImpl();
    } else if (myClass == AuthUserDto.class) {
      return new UserDtoToUserConverter();
    } else {
      throw new RuntimeException("Converter dont exist: " + myClass.getName());
    }
  }
}
