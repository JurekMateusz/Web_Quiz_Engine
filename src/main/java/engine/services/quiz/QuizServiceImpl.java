package engine.services.quiz;

import engine.dto.converter.Converter;
import engine.dto.converter.ConverterFactory;
import engine.dto.from.quiz.QuizFromUserDto;
import engine.dto.from.quiz.UserAnswer;
import engine.dto.to.QuizToUserDto;
import engine.dto.to.feedback.AnswerFeedback;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizAnswers;
import engine.exceptions.quiz.QuizDeleteForbiddenException;
import engine.exceptions.quiz.QuizNotFoundException;
import engine.repository.quiz.QuizRepository;
import engine.services.utils.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuizServiceImpl implements QuizService {
  private final QuizRepository quizRepository;

  @Autowired
  public QuizServiceImpl(QuizRepository quizRepository) {
    this.quizRepository = quizRepository;
  }

  @Override
  public AnswerFeedback checkAnswerById(long id, UserAnswer userAnswer) {
    return checkAnswer(id, userAnswer) ? AnswerFeedback.getSUCCESS() : AnswerFeedback.getFAILURE();
  }

  private boolean checkAnswer(long id, UserAnswer userAnswer) {
    Optional<Quiz> quiz = quizRepository.findById(id);
    if (quiz.isEmpty()) throw new QuizNotFoundException();
    Quiz quizWithId = quiz.get();
    List<Integer> quizWithIdAnswers =
        quizWithId.getAnswer().stream().map(QuizAnswers::getAnswer).collect(Collectors.toList());
    List<Integer> userAnswers =
        IntStream.of(userAnswer.getAnswer()).boxed().collect(Collectors.toList());
    return userAnswers.containsAll(quizWithIdAnswers)
        && userAnswers.size() == quizWithIdAnswers.size();
  }

  @Override
  public QuizToUserDto addQuiz(QuizFromUserDto quizFromUserDto) {
    Converter<QuizFromUserDto, Quiz> converterToQuiz =
        ConverterFactory.getConverter(QuizFromUserDto.class);
    Quiz quiz = converterToQuiz.convert(quizFromUserDto);
    setIdQuizMakerIfAuthenticated(quiz);

    quizRepository.save(quiz);

    Converter<Quiz, QuizToUserDto> converter = ConverterFactory.getConverter(Quiz.class);
    return converter.convert(quiz);
  }

  private void setIdQuizMakerIfAuthenticated(Quiz quiz) {
    if (!AuthenticatedUser.isAuthenticated()) {
      return;
    }
    setIdQuizMaker(quiz);
  }

  private void setIdQuizMaker(Quiz quiz) {
    long currentUserId = AuthenticatedUser.getId();
    quiz.setUserId(currentUserId);
  }

  @Override
  public QuizToUserDto getQuizById(long id) {
    Optional<Quiz> quiz = quizRepository.findById(id);
    if (quiz.isEmpty()) {
      throw new QuizNotFoundException();
    }
    Converter<Quiz, QuizToUserDto> converter = ConverterFactory.getConverter(Quiz.class);
    return converter.convert(quiz.get());
  }

  @Override
  public Page<QuizToUserDto> getAllQuizzes(Pageable pageable) {
    Page<Quiz> pageResult = quizRepository.findAll(pageable);
    return convertToDta(pageResult);
  }

  private Page<QuizToUserDto> convertToDta(Page<Quiz> page) {
    return page.map(
        e -> {
          Converter<Quiz, QuizToUserDto> converter = ConverterFactory.getConverter(Quiz.class);
          return converter.convert(e);
        });
  }

  @Override
  public void delete(long id) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
    if (optionalQuiz.isEmpty()) throw new QuizNotFoundException();
    Quiz quiz = optionalQuiz.get();
    long idAuthenticatedUser = AuthenticatedUser.getId();
    long quizUserId = quiz.getUserId();
    if (idAuthenticatedUser != quizUserId) {
      throw new QuizDeleteForbiddenException();
    }
    quizRepository.deleteById(quiz.getId());
  }
}
