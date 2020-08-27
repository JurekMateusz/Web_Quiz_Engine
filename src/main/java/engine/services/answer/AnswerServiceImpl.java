package engine.services.answer;

import com.sdp.common.assemblers.AssemblerFactory;
import engine.dto.converter.AssemblerWebQuizFactory;
import engine.dto.from.quiz.answer.UserAnswerDto;
import engine.dto.to.feedback.FeedbackForSolvedQuiz;
import engine.entity.answer.Answer;
import engine.entity.answer.QuestionAnswers;
import engine.entity.info.UserHelpQuizInfo;
import engine.entity.quiz.QuizQuestion;
import engine.repository.answers.UserQuestionAnswersRepository;
import engine.repository.quiz.entity.QuizAnswerQuestionRepository;
import engine.repository.quiz.entity.QuizQuestionRepository;
import engine.services.info.UserHelpQuizInfoService;
import engine.services.utils.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {
  private final UserQuestionAnswersRepository userQuestionAnswersRepository;
  private final QuizAnswerQuestionRepository answerQuestionRepository;
  private final QuizQuestionRepository questionRepository;
  private final UserHelpQuizInfoService infoService;

  @Autowired
  public AnswerServiceImpl(
          UserQuestionAnswersRepository userQuestionAnswersRepository,
          QuizAnswerQuestionRepository answerQuestionRepository,
          QuizQuestionRepository questionRepository,
          UserHelpQuizInfoService infoService) {
    this.userQuestionAnswersRepository = userQuestionAnswersRepository;
    this.answerQuestionRepository = answerQuestionRepository;
    this.questionRepository = questionRepository;
    this.infoService = infoService;
  }

  @Override
  @Transactional
  public void saveOrUpdate(UserAnswerDto userAnswerDto) {
    Optional<QuestionAnswers> answersOpt =
        userQuestionAnswersRepository.findByQuizQuestionId(userAnswerDto.getQuizQuestionId());
    if (answersOpt.isPresent()) {
      updateAnswer(answersOpt.get(), userAnswerDto);
    } else {
      createAnswer(userAnswerDto);
    }
  }

  private void updateAnswer(QuestionAnswers questionAnswers, UserAnswerDto userAnswerDto) {
    questionAnswers.getAnswers().clear();
    List<Answer> newAnswers =
        userAnswerDto.getAnswers().stream()
            .map(aLong -> Answer.builder().answerId(aLong).questionAnswers(questionAnswers).build())
            .collect(Collectors.toList());
    questionAnswers.getAnswers().addAll(newAnswers);
  }

  private void createAnswer(UserAnswerDto userAnswerDto) {
    QuestionAnswers questionAnswers = convertDto(userAnswerDto);
    questionAnswers.setUserId(AuthenticatedUser.getId());
    userQuestionAnswersRepository.save(questionAnswers);
  }

  private QuestionAnswers convertDto(UserAnswerDto userAnswerDto) {
    AssemblerFactory<UserAnswerDto, QuestionAnswers> converter =
        AssemblerWebQuizFactory.getConverter(UserAnswerDto.class);
    QuestionAnswers assemble = converter.assemble(userAnswerDto);
    assemble.getAnswers().forEach(answer -> answer.setQuestionAnswers(assemble));
    return assemble;
  }

  @Override
  public FeedbackForSolvedQuiz checkUserSavedAnswers(long quizId) {
    UserHelpQuizInfo userQuizInfo = infoService.getUserQuizInfo();
    if (userQuizInfo.getActualQuizId() != quizId) {
      throw new IllegalArgumentException(
          "Checked quiz_id not the same as in userQuizInfo.Quiz Id: "
              + quizId
              + ", UserInfo: "
              + userQuizInfo.getActualQuizId());
    }

    AtomicInteger correctAnswers = new AtomicInteger();
    AtomicInteger wrongAnswers = new AtomicInteger();
    AtomicInteger userAnswers = new AtomicInteger();
    LocalDateTime quizEndAt = LocalDateTime.now();

    List<QuizQuestion> questions = questionRepository.findAllByQuiz_Id(quizId);

    questions.forEach(
        quizQuestion -> {
          long questionId = quizQuestion.getId();
          Optional<QuestionAnswers> answersOpt =
              userQuestionAnswersRepository.findByQuizQuestionId(questionId);
          if (answersOpt.isEmpty()) return;

          List<Long> userAnswersId = getUserAnswers(answersOpt.get());
          List<Long> correctAnswersId =
              answerQuestionRepository.findAllCorrectAnswersIdWhereQuestion_IdIs(questionId);

          if (userAnswersId.containsAll(correctAnswersId)) {
            correctAnswers.getAndIncrement();
          } else if (!userAnswersId.isEmpty()) {
            wrongAnswers.getAndIncrement();
          }
          userAnswers.getAndIncrement();
        });

    return FeedbackForSolvedQuiz.builder()
        .startedAt(userQuizInfo.getQuizStartedAt())
        .completedAt(quizEndAt)
        .correctAnswers(correctAnswers.get())
        .numberOfQuestions(questions.size())
        .wrongAnswers(wrongAnswers.get())
        .numberOfAnswers(userAnswers.get())
        .build();
  }

  private List<Long> getUserAnswers(QuestionAnswers questionAnswers) {
    return questionAnswers.getAnswers().stream()
        .map(Answer::getAnswerId)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteUserAnswersForQuizId(long id) {
    if (id != 0) {
      answerQuestionRepository.deleteAllByQuizQuestion_Id(id);
    }
  }
}
