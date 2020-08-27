package engine.controllers.quiz;

import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.from.quiz.answer.UserAnswerDto;
import engine.dto.to.feedback.FeedbackForSolvedQuiz;
import engine.dto.to.quiz.full.FullQuizToUserDto;
import engine.dto.to.quiz.full.QuizAnswerQuestionDto;
import engine.dto.to.quiz.header.QuizHeaderDto;
import engine.dto.to.quiz.questions.QuizQuestionsDto;
import engine.entity.complete.CompleteQuizInfo;
import engine.services.answer.AnswerService;
import engine.services.complete.CompleteQuizInfoService;
import engine.services.info.UserHelpQuizInfoService;
import engine.services.quiz.QuizService;
import engine.services.utils.AuthenticatedUser;
import engine.util.Parameters;
import engine.util.quizzes.QuizzesMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuizController {
  private final QuizService quizService;
  private final CompleteQuizInfoService completeQuizInfoService;
  private final AnswerService answerService;
  private final UserHelpQuizInfoService infoService;

  @Autowired
  public QuizController(
      QuizService quizService,
      CompleteQuizInfoService completeQuizInfoService,
      AnswerService answerService,
      UserHelpQuizInfoService infoService) {
    this.quizService = quizService;
    this.completeQuizInfoService = completeQuizInfoService;
    this.answerService = answerService;
    this.infoService = infoService;
  }

  @PostMapping(path = QuizzesMapping.BASIC_QUIZZES_PATH, consumes = Parameters.JSON)
  @ResponseStatus(HttpStatus.CREATED)
  public void addQuiz(@RequestBody @Valid AddQuizDto quizFromUser) {
    quizService.addQuizzes(quizFromUser);
  }

  @GetMapping(QuizzesMapping.QUIZ_BY_ID)
  public FullQuizToUserDto getFullQuiz(@PathVariable long id) {
    return quizService.getQuizById(id);
  }

  @GetMapping(QuizzesMapping.BASIC_QUIZZES_PATH)
  public Page<QuizHeaderDto> getAllQuizzes(Pageable pageable) {
    return quizService.getAllHeadersQuizzes(pageable);
  }

  @GetMapping(QuizzesMapping.ALL_QUIZ_QUESTIONS)
  public List<QuizQuestionsDto> getAllQuizQuestions(@PathVariable long id) {
    deleteLastUserAnswersAndUpdateUserInfo(id);
    return quizService.getAllQuizQuestions(id);
  }

  @Async
  void deleteLastUserAnswersAndUpdateUserInfo(long id) {
    long idLastQuiz = infoService.getIdLastDoneQuiz();
    answerService.deleteUserAnswersForQuizId(idLastQuiz);
    infoService.updateLastQuizId(id);
  }

  @GetMapping(QuizzesMapping.QUESTION_ANSWERS)
  public List<QuizAnswerQuestionDto> getAnswers(@PathVariable long id) {
    return quizService.getQuizAnswers(id);
  }

  @GetMapping(QuizzesMapping.ALL_COMPLETED_QUIZZES)
  public Page<CompleteQuizInfo> getAllCompleted(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "completedAt") String sortBy) {
    return completeQuizInfoService.getPage(page, size, sortBy);
  }

  @PostMapping(QuizzesMapping.SAVE_USER_ANSWER)
  @ResponseStatus(HttpStatus.CREATED)
  public void saveUserAnswer(@RequestBody UserAnswerDto userAnswerDto) {
    answerService.saveOrUpdate(userAnswerDto);
  }

  @GetMapping(QuizzesMapping.END_QUIZ)
  public FeedbackForSolvedQuiz checkUserAnswers() {
    long quizId = infoService.getIdLastDoneQuiz();
    FeedbackForSolvedQuiz feedback = answerService.checkUserSavedAnswers(quizId);
    long userId = AuthenticatedUser.getId();
    completeQuizInfoService.setResultToUserStats(userId,quizId, feedback);
    return feedback;
  }

  @DeleteMapping(QuizzesMapping.DELETE_QUIZ)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteQuiz(@PathVariable long id) {
    quizService.delete(id);
  }
}
