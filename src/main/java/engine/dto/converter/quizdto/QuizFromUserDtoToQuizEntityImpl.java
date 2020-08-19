package engine.dto.converter.quizdto;

import engine.dto.converter.Converter;
import engine.dto.from.quiz.QuizFromUserDto;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizAnswers;
import engine.entity.quiz.QuizOptions;

import java.util.List;
import java.util.stream.Collectors;

public class QuizFromUserDtoToQuizEntityImpl implements Converter<QuizFromUserDto, Quiz> {
  @Override
  public Quiz convert(QuizFromUserDto objectToConvert) {
    Quiz result = new Quiz();

    String title = objectToConvert.getTitle();
    String text = objectToConvert.getText();
    List<QuizOptions> options = convertStringToQuizOptionsList(objectToConvert, result);
    List<QuizAnswers> answers = convertIntegerToQuizAnswerList(objectToConvert, result);

    result.setTitle(title);
    result.setQuestion(text);
    result.setOptions(options);
    result.setAnswer(answers);

    return result;
  }

  private List<QuizOptions> convertStringToQuizOptionsList(
      QuizFromUserDto objectToConvert, Quiz result) {
    return objectToConvert.getOptions().stream()
        .map(e -> QuizOptions.builder().content(e).quiz(result).build())
        .collect(Collectors.toList());
  }

  private List<QuizAnswers> convertIntegerToQuizAnswerList(
      QuizFromUserDto objectToConvert, Quiz result) {
    return objectToConvert.getAnswer().stream()
        .map(e -> QuizAnswers.builder().answer(e).quiz(result).build())
        .collect(Collectors.toList());
  }
}
