package engine.services.quiz;

import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.to.quiz.full.FullQuizToUserDto;
import engine.dto.to.quiz.full.QuizAnswerQuestionDto;
import engine.dto.to.quiz.header.QuizHeaderDto;
import engine.dto.to.quiz.questions.QuizQuestionsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizService {
  void addQuizzes(AddQuizDto addQuizDto);

  FullQuizToUserDto getQuizById(long id);

  Page<QuizHeaderDto> getAllHeadersQuizzes(Pageable pageable);

  void delete(long id);

  List<QuizQuestionsDto> getAllQuizQuestions(long id);

  List<QuizAnswerQuestionDto> getQuizAnswers(long id);
}
