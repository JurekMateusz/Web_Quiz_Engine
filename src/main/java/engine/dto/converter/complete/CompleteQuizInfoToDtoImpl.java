package engine.dto.converter.complete;

import engine.dto.converter.Converter;
import engine.dto.to.quiz.CompleteQuizInfoDto;
import engine.entity.complete.CompleteQuizInfo;

public class CompleteQuizInfoToDtoImpl implements Converter<CompleteQuizInfo, CompleteQuizInfoDto> {
  @Override
  public CompleteQuizInfoDto convert(CompleteQuizInfo objectToConvert) {
    CompleteQuizInfoDto result = new CompleteQuizInfoDto();
    result.setId(objectToConvert.getQuizId());
    result.setCompletedAt(objectToConvert.getCompletedAt());
    return result;
  }
}
