package engine.dto.converter.quiz;

import engine.dto.converter.Converter;
import engine.dto.to.quiz.QuizToUserDto;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizOptions;

public class QuizEntityToQuizDtoImpl implements Converter<Quiz, QuizToUserDto> {

  @Override
  public QuizToUserDto convert(Quiz objectToConvert) {
    String title = objectToConvert.getTitle();
    String text = objectToConvert.getQuestion();
    String[] options = convertContentToStringArray(objectToConvert);
    long id = objectToConvert.getId();
    return QuizToUserDto.builder().id(id).title(title).text(text).options(options).build();
  }

  private String[] convertContentToStringArray(Quiz objectToConvert) {
    return objectToConvert.getOptions().stream()
        .map(QuizOptions::getContent)
        .toArray(String[]::new);
  }
}
