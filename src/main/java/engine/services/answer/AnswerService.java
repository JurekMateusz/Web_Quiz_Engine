package engine.services.answer;

import engine.dto.from.quiz.answer.UserAnswerDto;
import engine.dto.to.feedback.FeedbackForSolvedQuiz;
import org.springframework.scheduling.annotation.Async;

public interface AnswerService {
  void saveOrUpdate(UserAnswerDto userAnswerDto);

  FeedbackForSolvedQuiz checkUserSavedAnswers(long quiz_id);

  void deleteUserAnswersForQuizId(long id);
}
