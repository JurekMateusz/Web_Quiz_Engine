package engine.controllers.quiz;

import engine.dto.from.QuizFromUserDto;
import engine.dto.from.UserAnswer;
import engine.dto.to.CompleteQuizInfoDto;
import engine.dto.to.QuizToUserDto;
import engine.dto.to.feedback.AnswerFeedback;
import engine.services.complete.CompleteQuizInfoService;
import engine.services.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
  private final QuizService quizService;
  private final CompleteQuizInfoService quizInfoService;

  @Autowired
  public QuizController(QuizService quizService, CompleteQuizInfoService completeQuizInfoService) {
    this.quizService = quizService;
    this.quizInfoService = completeQuizInfoService;
  }

  @PostMapping("/{id}/solve")
  public AnswerFeedback checkAnswer(@PathVariable long id, @RequestBody UserAnswer userAnswer) {
    AnswerFeedback answerFeedback = quizService.checkAnswerById(id, userAnswer);
    if (answerFeedback.isSuccess()) {
      quizInfoService.addIdQuizToUserCompletedQuizzes(id);
    }
    return answerFeedback;
  }

  @PostMapping(consumes = "application/json")
  public QuizToUserDto addQuiz(@RequestBody @Valid QuizFromUserDto quizFromUser) {
    return quizService.addQuiz(quizFromUser);
  }

  @GetMapping("/{id}")
  public QuizToUserDto getQuiz(@PathVariable long id) {
    return quizService.getQuizById(id);
  }

  @GetMapping
  public Page<QuizToUserDto> getAllQuizzes(Pageable pageable) {
    return quizService.getAllQuizzes(pageable);
  }

  @GetMapping("/completed")
  public Page<CompleteQuizInfoDto> getAllCompleted(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "completedAt") String sortBy) {
    return quizInfoService.getAll(page, size, sortBy);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteQuiz(@PathVariable long id) {
    quizService.delete(id);
  }
}
