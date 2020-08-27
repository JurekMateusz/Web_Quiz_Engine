package engine.services.complete;

import engine.dto.to.feedback.FeedbackForSolvedQuiz;
import engine.entity.complete.CompleteQuizInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompleteQuizInfoService {

  Page<CompleteQuizInfo> getPage(int pageNo, int pageSize, String sortBy);

  List<CompleteQuizInfo> getAllUserCompletedQuizzes();

  void setResultToUserStats(long userId, long quizId, FeedbackForSolvedQuiz feedback);
}
