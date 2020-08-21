package engine.services.quiz;

import engine.dto.from.quiz.answer.UserAnswer;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.to.feedback.FeedbackAnswerForSingleQuiz;
import engine.dto.to.quiz.FullQuizToUserDto;
import engine.entity.quiz.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizService {
  FeedbackAnswerForSingleQuiz checkAnswerSingleQuizById(long id, UserAnswer userAnswer);

  void addQuizzes(AddQuizDto addQuizDto);

  FullQuizToUserDto getQuizById(long id);

  Page<Quiz> getAllQuizzes(Pageable pageable);

  void delete(long id);

  //  List<QuizQuestion> searchAllSingleQuizzes();
  //  List<QuizAnswerQuestion> searchAllQuizAnswerQuestion();
}
