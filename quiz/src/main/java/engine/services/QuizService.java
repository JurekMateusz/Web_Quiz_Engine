package engine.services;

import engine.dto.QuizFromUser;
import engine.dto.QuizToUser;
import engine.dto.UserAnswer;
import engine.dto.feedback.QuizFeedback;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizAnswers;
import engine.exceptions.QuizNotFoundException;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class QuizService {
    private QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
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
        quizRepository.save(quiz);
        return new QuizToUser(quiz);
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

}

