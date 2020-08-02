package engine.services;

import engine.dto.QuizFeedback;
import engine.dto.QuizFromUser;
import engine.dto.QuizToUser;
import engine.dto.UserAnswer;
import engine.entity.Quiz;
import engine.exceptions.QuizNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuizService {
    private List<Quiz> allQuizzes;

    public QuizService() {
        allQuizzes = new ArrayList<>();
    }

    public QuizFeedback checkAnswerById(long id, UserAnswer userAnswer) {
        return checkAnswer(id, userAnswer) ? QuizFeedback.getSUCCESS() : QuizFeedback.getFAILURE();
    }

    private boolean checkAnswer(long id, UserAnswer userAnswer) {
        Optional<Quiz> quiz = allQuizzes.stream()
                .filter(q -> q.getId() == id)
                .findFirst();
        if (quiz.isEmpty()) throw new QuizNotFoundException();
        Quiz quizWithId = quiz.get();
        List<Integer> quizWithIdAnswers = IntStream.of(quizWithId.getAnswer()).boxed().collect(Collectors.toList());
        List<Integer> userAnswers = IntStream.of(userAnswer.getAnswer()).boxed().collect(Collectors.toList());
        return userAnswers.containsAll(quizWithIdAnswers) && userAnswers.size() == quizWithIdAnswers.size();
    }

    public QuizToUser addQuiz(QuizFromUser quizFromUser) {
        Quiz quiz = new Quiz(quizFromUser);
        quiz.setId(allQuizzes.size() + 1);
        allQuizzes.add(quiz);
        return new QuizToUser(quiz);
    }

    public QuizToUser getQuizById(long id) {
        try {
            return new QuizToUser(allQuizzes.get((int) id - 1));
        } catch (IndexOutOfBoundsException ex) {
            throw new QuizNotFoundException();
        }
    }

    public List<QuizToUser> getAllQuizzes() {
        return allQuizzes.stream()
                .map(QuizToUser::new)
                .collect(Collectors.toList());
    }

}

