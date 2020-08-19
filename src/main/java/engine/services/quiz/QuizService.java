package engine.services.quiz;

import engine.dto.from.quiz.QuizFromUserDto;
import engine.dto.from.quiz.UserAnswer;
import engine.dto.to.QuizToUserDto;
import engine.dto.to.feedback.AnswerFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizService {
  AnswerFeedback checkAnswerById(long id, UserAnswer userAnswer);

  QuizToUserDto addQuiz(QuizFromUserDto quizFromUserDto);

  QuizToUserDto getQuizById(long id);

  Page<QuizToUserDto> getAllQuizzes(Pageable pageable);

  void delete(long id);
}
