package engine.services.quiz;

import engine.dto.QuizFromUser;
import engine.dto.QuizToUser;
import engine.dto.UserAnswer;
import engine.dto.feedback.QuizFeedback;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizAnswers;
import engine.exceptions.quiz.QuizDeleteForbiddenException;
import engine.exceptions.quiz.QuizNotFoundException;
import engine.repository.quiz.QuizRepository;
import engine.security.userdetails.UserPrincipal;
import engine.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class QuizService {
    private QuizRepository quizRepository;
    private UserService userService;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserService userService) {
        this.quizRepository = quizRepository;
        this.userService = userService;
    }

    public QuizFeedback checkAnswerById(long id, UserAnswer userAnswer) {
        return checkAnswer(id, userAnswer) ? QuizFeedback.getSUCCESS() : QuizFeedback.getFAILURE();
    }

    private boolean checkAnswer(long id, UserAnswer userAnswer) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) throw new QuizNotFoundException();
        Quiz quizWithId = quiz.get();
        List<Integer> quizWithIdAnswers = quizWithId.getAnswer().stream()
                .map(QuizAnswers::getAnswer)
                .collect(Collectors.toList());
        List<Integer> userAnswers = IntStream.of(userAnswer.getAnswer())
                .boxed()
                .collect(Collectors.toList());
        return userAnswers.containsAll(quizWithIdAnswers) && userAnswers.size() == quizWithIdAnswers.size();
    }

    public QuizToUser addQuiz(QuizFromUser quizFromUser) {
        Quiz quiz = new Quiz(quizFromUser);
        setIdQuizMakerIfAuthenticated(quiz);
        quizRepository.save(quiz);
        return new QuizToUser(quiz);
    }

    private void setIdQuizMakerIfAuthenticated(Quiz quiz) {
        if (!isAuthenticated()) {
            return;
        }
        setIdQuizMaker(quiz);
    }

    private boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }


    private void setIdQuizMaker(Quiz quiz) {
        long currentUserId = findIdAuthenticatedUser();
        quiz.setUserId(currentUserId);
    }

    private long findIdAuthenticatedUser() {
        Authentication authentication = getAuthentication();
        return ((UserPrincipal) authentication.getPrincipal()).getId();
    }


    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public QuizToUser getQuizById(long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException();
        }
        return new QuizToUser(quiz.get());
    }

    public List<QuizToUser> getAllQuizzes() {
        Iterable<Quiz> quizzes = quizRepository.findAll();
        return StreamSupport.stream(quizzes.spliterator(), false)
                .map(QuizToUser::new)
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isEmpty()) throw new QuizNotFoundException();
        Quiz quiz = optionalQuiz.get();
        long idAuthenticatedUser = findIdAuthenticatedUser();
        long quizUserId = quiz.getUserId();
        if (idAuthenticatedUser != quizUserId) {
            throw new QuizDeleteForbiddenException();
        }
        quizRepository.deleteById(quiz.getId());
    }

}

