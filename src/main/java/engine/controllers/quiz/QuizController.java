package engine.controllers.quiz;

import engine.dto.from.quiz.answer.UserAnswer;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.to.feedback.FeedbackAnswerForSingleQuiz;
import engine.dto.to.quiz.CompleteQuizInfoDto;
import engine.dto.to.quiz.FullQuizToUserDto;
import engine.entity.quiz.Quiz;
import engine.services.complete.CompleteQuizInfoService;
import engine.services.quiz.QuizService;
import engine.util.Parameters;
import engine.util.quizzes.QuizzesMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class QuizController {
  private final QuizService quizService;
  private final CompleteQuizInfoService quizInfoService;

  @Autowired
  public QuizController(QuizService quizService, CompleteQuizInfoService completeQuizInfoService) {
    this.quizService = quizService;
    this.quizInfoService = completeQuizInfoService;
  }

  @PostMapping(path = QuizzesMapping.BASIC_QUIZZES_PATH, consumes = Parameters.JSON)
  @ResponseStatus(HttpStatus.CREATED)
  public void addQuizzes(@RequestBody @Valid AddQuizDto quizFromUser) {
    quizService.addQuizzes(quizFromUser);
  }

  @GetMapping(QuizzesMapping.BASIC_QUIZZES_PATH)
  public Page<Quiz> getAllQuizzes(Pageable pageable) {
    return quizService.getAllQuizzes(pageable);
  }

  @GetMapping(QuizzesMapping.QUIZ_BY_ID)
  public FullQuizToUserDto getFullQuiz(@PathVariable long id) {
    return quizService.getQuizById(id);
  }

  @GetMapping(QuizzesMapping.ALL_COMPLETED_QUIZZES)
  public Page<CompleteQuizInfoDto> getAllCompleted(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "completedAt") String sortBy) {
    return quizInfoService.getAll(page, size, sortBy);
  }

  @PostMapping(QuizzesMapping.CHECK_ANSWER_QUESTION)
  public FeedbackAnswerForSingleQuiz checkAnswerSingleQuiz(
      @PathVariable long id, @RequestBody UserAnswer userAnswer) {
    FeedbackAnswerForSingleQuiz feedbackAnswerForSingleQuiz =
        quizService.checkAnswerSingleQuizById(id, userAnswer);
    //TODO
//    if (feedbackAnswerForSingleQuiz.isSuccess()) {
//      quizInfoService.addIdQuizToUserCompletedQuizzes(id);
//    }
    return feedbackAnswerForSingleQuiz;
  }

  @DeleteMapping(QuizzesMapping.DELETE_QUIZ)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteQuiz(@PathVariable long id) {
    quizService.delete(id);
  }
}
