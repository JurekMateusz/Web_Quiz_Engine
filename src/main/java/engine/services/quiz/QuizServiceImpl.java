package engine.services.quiz;

import com.sdp.common.assemblers.AssemblerFactory;
import engine.dto.converter.quiz.quizdto.AddQuizDtoAssemblerFactory;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.to.quiz.full.FullQuizToUserDto;
import engine.dto.to.quiz.full.QuizAnswerQuestionDto;
import engine.dto.to.quiz.full.QuizQuestionToUserDto;
import engine.dto.to.quiz.header.QuizHeaderDto;
import engine.dto.to.quiz.questions.QuizQuestionsDto;
import engine.entity.complete.CompleteQuizInfo;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizAnswerQuestion;
import engine.entity.quiz.QuizQuestion;
import engine.exceptions.quiz.QuizDeleteForbiddenException;
import engine.exceptions.quiz.QuizNotFoundException;
import engine.repository.quiz.entity.QuizAnswerQuestionRepository;
import engine.repository.quiz.entity.QuizQuestionRepository;
import engine.repository.quiz.entity.QuizRepository;
import engine.services.complete.CompleteQuizInfoService;
import engine.services.utils.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static engine.dto.converter.AssemblerWebQuizFactory.getConverter;

@Service
@Component
public class QuizServiceImpl implements QuizService {
  private final QuizRepository quizRepo;
  private final QuizAnswerQuestionRepository quizAnswerQuestionRepo;
  private final QuizQuestionRepository quizQuestionRepo;
  private final CompleteQuizInfoService completeQuizInfoService;
  @Autowired
  public QuizServiceImpl(
      QuizRepository quizRepo,
      QuizAnswerQuestionRepository quizAnswerQuestionRepo,
      QuizQuestionRepository quizQuestionRepo,
      CompleteQuizInfoService completeQuizInfoService) {
    this.quizRepo = quizRepo;
    this.quizAnswerQuestionRepo = quizAnswerQuestionRepo;
    this.quizQuestionRepo = quizQuestionRepo;
    this.completeQuizInfoService = completeQuizInfoService;
  }

  @Override
  @Transactional
  public void addQuizzes(AddQuizDto addQuizDto) {
    long userId = AuthenticatedUser.getId();
    AddQuizDtoAssemblerFactory converter =
        (AddQuizDtoAssemblerFactory) getConverter(AddQuizDto.class);
    HashMap<Class<?>, List> convertedHashMap = converter.convert(addQuizDto);

    List<QuizQuestion> quizQuestions = convertedHashMap.get(QuizQuestion.class);
    Quiz quiz = quizQuestions.get(0).getQuiz();
    quiz.setUserId(userId);
    quiz.setNumbersOfQuestions(quizQuestions.size());
    quizRepo.save(quiz);

    quizQuestions.forEach(quizQuestionRepo::save);

    List<List<QuizAnswerQuestion>> quizAnswerQuestions = convertedHashMap.get(QuizAnswerQuestion.class);
    quizAnswerQuestions.forEach(quizAnswerQuestionRepo::saveAll);
  }

  @Override
  @Transactional(readOnly = true)
  public FullQuizToUserDto getQuizById(long id) {
    List<QuizQuestion> quizQuestions = quizQuestionRepo.findAllByQuiz_Id(id);

    if (quizQuestions.isEmpty()) {
      throw new QuizNotFoundException();
    }

    List<QuizQuestionToUserDto> quizQuestionsDto = convertQuizQuestionList(quizQuestions);

    Quiz quiz = quizQuestions.get(0).getQuiz();
    long quizId = quiz.getId();
    String quizTitle = quiz.getTitle();
    return FullQuizToUserDto.of(quizId, quizTitle, quizQuestionsDto);
  }

  private List<QuizQuestionToUserDto> convertQuizQuestionList(List<QuizQuestion> list) {
    return list.stream()
        .map(
            quizQuestion -> {
              QuizQuestionToUserDto quizQuestionToUserDto = new QuizQuestionToUserDto();
              quizQuestionToUserDto.setQuestion(quizQuestion.getQuestion());

              List<QuizAnswerQuestion> list1 =
                  quizAnswerQuestionRepo.findAllByQuizQuestion_Id(quizQuestion.getId());
              List<QuizAnswerQuestionDto> quizAnswerToUserDtoList = covertQuizAnswerList(list1);

              quizQuestionToUserDto.setQuizAnswerQuestions(quizAnswerToUserDtoList);
              return quizQuestionToUserDto;
            })
        .collect(Collectors.toList());
  }

  private List<QuizAnswerQuestionDto> covertQuizAnswerList(List<QuizAnswerQuestion> list) {
    return list.stream()
        .map(
            quizAnswerQuestion -> {
              QuizAnswerQuestionDto quizAnswer = new QuizAnswerQuestionDto();
              quizAnswer.setAnswer(quizAnswerQuestion.getAnswer());
              quizAnswer.setId(quizAnswerQuestion.getId());
              return quizAnswer;
            })
        .collect(Collectors.toList());
  }

  @Override
  public Page<QuizHeaderDto> getAllHeadersQuizzes(Pageable pageable) {
    Page<Quiz> all = quizRepo.findAll(pageable);
    List<CompleteQuizInfo> allCompleted = completeQuizInfoService.getAllUserCompletedQuizzes();

    return all.map(
        quiz -> {
          QuizHeaderDto converted = convertQuiz(quiz);
          if (isQuizCompleted(quiz, allCompleted)) {
            converted.setMade(true);
          }
          return converted;
        });
  }

  private QuizHeaderDto convertQuiz(Quiz quiz) {
    AssemblerFactory<Quiz, QuizHeaderDto> converter = getConverter(Quiz.class);

    return converter.assemble(quiz);
  }

  private boolean isQuizCompleted(Quiz quiz, List<CompleteQuizInfo> completed) {
    return completed.stream()
        .anyMatch(completeQuizInfo -> completeQuizInfo.getQuizId() == quiz.getId());
  }

  @Override
  @Transactional
  public void delete(long id) {
    Optional<Quiz> optionalQuiz = quizRepo.findById(id);
    if (optionalQuiz.isEmpty()) throw new QuizNotFoundException();
    Quiz quiz = optionalQuiz.get();
    long idAuthenticatedUser = AuthenticatedUser.getId();
    long quizUserId = quiz.getUserId();
    if (idAuthenticatedUser != quizUserId) {
      throw new QuizDeleteForbiddenException();
    }

    deleteFullQuiz(quiz);
  }

  @Override
  public List<QuizQuestionsDto> getAllQuizQuestions(long id) {
    List<QuizQuestion> questions = quizQuestionRepo.findAllByQuiz_Id(id);
    return convertQuestions(questions);
  }

  private List<QuizQuestionsDto> convertQuestions(List<QuizQuestion> questions) {
    AssemblerFactory<QuizQuestion, QuizQuestionsDto> converter = getConverter(QuizQuestion.class);
    return questions.stream().map(converter::assemble).collect(Collectors.toList());
  }

  private void deleteFullQuiz(Quiz quiz) {
    List<QuizQuestion> allByQuiz_id = quizQuestionRepo.findAllByQuiz_Id(quiz.getId());
    allByQuiz_id.forEach(
        quizQuestion -> {
          quizAnswerQuestionRepo.deleteAllByQuizQuestion_Id(quizQuestion.getId());
          quizQuestionRepo.delete(quizQuestion);
        });
    quizRepo.delete(quiz);
  }

  @Override
  public List<QuizAnswerQuestionDto> getQuizAnswers(long id) {
    List<QuizAnswerQuestion> answers = quizAnswerQuestionRepo.findAllByQuizQuestion_Id(id);
    return convertToDto(answers);
  }

  private List<QuizAnswerQuestionDto> convertToDto(List<QuizAnswerQuestion> answers) {
    return answers.stream()
        .map(answer -> QuizAnswerQuestionDto.of(answer.getId(), answer.getAnswer()))
        .collect(Collectors.toList());
  }
}
