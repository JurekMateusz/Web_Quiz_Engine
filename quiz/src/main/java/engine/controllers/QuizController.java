package engine.controllers;

import engine.dto.feedback.QuizFeedback;
import engine.dto.QuizFromUser;
import engine.dto.QuizToUser;
import engine.dto.UserAnswer;
import engine.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
public class QuizController {
    private QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public QuizFeedback checkAnswer(@PathVariable long id, @RequestBody UserAnswer userAnswer) {
        return quizService.checkAnswerById(id, userAnswer);
    }

    @PostMapping(value = "/api/quizzes", consumes = "application/json")
    public QuizToUser addQuiz(@Valid @NotEmpty @RequestBody QuizFromUser quizFromUser) {
        return quizService.addQuiz(quizFromUser);
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizToUser getQuiz(@PathVariable long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/api/quizzes")
    public List<QuizToUser> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }
}