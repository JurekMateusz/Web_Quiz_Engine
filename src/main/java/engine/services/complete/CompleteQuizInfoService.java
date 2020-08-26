package engine.services.complete;

import engine.dto.to.quiz.CompleteQuizInfoDto;
import engine.entity.complete.CompleteQuizInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompleteQuizInfoService {
  void addIdQuizToUserCompletedQuizzes(long quizId);

  Page<CompleteQuizInfoDto> getPage(int pageNo, int pageSize, String sortBy);

  List<CompleteQuizInfo> getAllUserCompletedQuizzes();
}
