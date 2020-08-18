package engine.services.complete;

import engine.dto.to.CompleteQuizInfoDto;
import org.springframework.data.domain.Page;

public interface CompleteQuizInfoService {
  void addIdQuizToUserCompletedQuizzes(long quizId);

  Page<CompleteQuizInfoDto> getAll(int pageNo, int pageSize, String sortBy);
}
