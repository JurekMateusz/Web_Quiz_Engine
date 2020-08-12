package engine.controllers;

import engine.entities.Quiz;
import engine.exceptions.QuizNotFoundException;
import engine.todo.QuizFeedback;
import engine.todo.QuizFromUser;
import engine.todo.QuizToUser;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class QuizController {
    private List<Quiz> allQuizzes;

    public QuizController() {
        allQuizzes = new ArrayList<>();
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public QuizFeedback correctAnswerCheck(@PathVariable long id, @RequestParam int answer) {
        return isCorrectAnswer(id, answer) ? QuizFeedback.getSuccess() : QuizFeedback.getFailure();
    }

    private boolean isCorrectAnswer(long id, int answer) {
        Optional<Quiz> quiz = allQuizzes.stream()
                .filter(q -> q.getId() == id)
                .findFirst();
        if (quiz.isEmpty()) throw new QuizNotFoundException();
        return quiz.get().getAnswer() == answer;
    }

    @PostMapping(value = "/api/quizzes", consumes = "application/json")
    public QuizToUser addQuiz(@RequestBody QuizFromUser quizFromUser) {
        Quiz quiz = new Quiz(quizFromUser);
        quiz.setId(allQuizzes.size() + 1);
        allQuizzes.add(quiz);

        return new QuizToUser(quiz);
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizToUser getQuiz(@PathVariable long id) {
        try {
            return new QuizToUser(allQuizzes.get((int) id - 1));
        } catch (IndexOutOfBoundsException ex) {
            throw new QuizNotFoundException();
        }
    }

    @GetMapping("/api/quizzes")
    public List<QuizToUser> getAllQuizzes() {
        return allQuizzes.stream()
                .map(QuizToUser::new)
                .collect(Collectors.toList());
    }
}