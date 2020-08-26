package engine.services.quiz;

import com.sdp.common.assemblers.AssemblerFactory;
import engine.dto.converter.AssemblerWebQuizFactory;
import engine.dto.converter.quizdto.AddQuizDtoAssemblerFactory;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.from.quiz.answer.UserAnswer;
import engine.dto.to.feedback.FeedbackAnswerForSingleQuiz;
import engine.dto.to.quiz.QuizHeaderDto;
import engine.dto.to.quiz.full.FullQuizToUserDto;
import engine.dto.to.quiz.full.QuizAnswerQuestionToUserDto;
import engine.dto.to.quiz.full.QuizQuestionToUserDto;
import engine.entity.complete.CompleteQuizInfo;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizAnswerQuestion;
import engine.entity.quiz.QuizQuestion;
import engine.exceptions.quiz.QuizDeleteForbiddenException;
import engine.exceptions.quiz.QuizNotFoundException;
import engine.exceptions.quiz.QuizQuestionNotFoundException;
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
            QuizQuestionRepository quizQuestionRepo, CompleteQuizInfoService completeQuizInfoService) {
        this.quizRepo = quizRepo;
        this.quizAnswerQuestionRepo = quizAnswerQuestionRepo;
        this.quizQuestionRepo = quizQuestionRepo;
        this.completeQuizInfoService = completeQuizInfoService;
    }

    @Override
    public FeedbackAnswerForSingleQuiz checkAnswerSingleQuizById(long id, UserAnswer userAnswer) {
        return checkAnswer(id, userAnswer)
                ? FeedbackAnswerForSingleQuiz.getSUCCESS()
                : FeedbackAnswerForSingleQuiz.getFAILURE();
    }

    private boolean checkAnswer(long id, UserAnswer userAnswer) {
        Optional<QuizQuestion> quiz = quizQuestionRepo.findById(id);
        if (quiz.isEmpty()) throw new QuizQuestionNotFoundException();

        List<Long> idAllCorrectAnswers = getIdAllCorrectAnswers(id);
        List<Long> answers = userAnswer.getAnswer();
        return isAnswerCorrect(answers, idAllCorrectAnswers);
    }

    private List<Long> getIdAllCorrectAnswers(long questionId) {
        return quizAnswerQuestionRepo.findAllByQuestion_idAndStatus_Correct(questionId).stream()
                .map(QuizAnswerQuestion::getId)
                .collect(Collectors.toList());
    }

    private boolean isAnswerCorrect(List<Long> answers, List<Long> correctAnswers) {
        return answers.containsAll(correctAnswers) && answers.size() == correctAnswers.size();
    }

    @Override
    @Transactional
    public void addQuizzes(AddQuizDto addQuizDto) {
        long userId = AuthenticatedUser.getId();
        AddQuizDtoAssemblerFactory converter =
                (AddQuizDtoAssemblerFactory) AssemblerWebQuizFactory.getConverter(AddQuizDto.class);
        HashMap<Class<?>, List> convertedHashMap = converter.convert(addQuizDto);

        List<QuizQuestion> quizQuestions = convertedHashMap.get(QuizQuestion.class);
        Quiz quiz = quizQuestions.get(0).getQuiz();
        quiz.setUserId(userId);
        quiz.setNumbersOfQuestions(quizQuestions.size());
        quizRepo.save(quiz);

        quizQuestions.forEach(quizQuestionRepo::save);

        List<QuizAnswerQuestion> quizAnswerQuestions = convertedHashMap.get(QuizAnswerQuestion.class);
        quizAnswerQuestions.forEach(quizAnswerQuestionRepo::save);
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
                            List<QuizAnswerQuestionToUserDto> quizAnswerToUserDtoList =
                                    covertQuizAnswerList(list1);

                            quizQuestionToUserDto.setQuizAnswerQuestions(quizAnswerToUserDtoList);
                            return quizQuestionToUserDto;
                        })
                .collect(Collectors.toList());
    }

    private List<QuizAnswerQuestionToUserDto> covertQuizAnswerList(List<QuizAnswerQuestion> list) {
        return list.stream()
                .map(
                        quizAnswerQuestion -> {
                            QuizAnswerQuestionToUserDto quizAnswer = new QuizAnswerQuestionToUserDto();
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

       return all.map(quiz -> {
            QuizHeaderDto converted = convertQuiz(quiz);
            if(isQuizCompleted(quiz,allCompleted)){
               converted.setMade(true);
            }
            return converted;
        });
    }
    private QuizHeaderDto convertQuiz(Quiz quiz){
        AssemblerFactory<Quiz, QuizHeaderDto> converter = AssemblerWebQuizFactory.getConverter(QuizHeaderDto.class);
        return converter.assemble(quiz);
    }
    private boolean isQuizCompleted(Quiz quiz, List<CompleteQuizInfo> completed){
       return  completed.stream()
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

    private void deleteFullQuiz(Quiz quiz) {
        List<QuizQuestion> allByQuiz_id = quizQuestionRepo.findAllByQuiz_Id(quiz.getId());
        allByQuiz_id.forEach(
                quizQuestion -> {
                    quizAnswerQuestionRepo.deleteAllByQuizQuestion_Id(quizQuestion.getId());
                    quizQuestionRepo.delete(quizQuestion);
                });
        quizRepo.delete(quiz);
    }
}
