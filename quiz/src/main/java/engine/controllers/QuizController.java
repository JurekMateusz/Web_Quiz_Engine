package engine.controllers;

import engine.entities.Quiz;
import engine.todo.QuizResult;
import engine.todo.QuizToSend;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {
    private Quiz quiz;

    public QuizController() {
        quiz = new Quiz(
                "The Java Logo",
                "What is depicted on the Java logo?",
                new String[]{"Robot", "Tea leaf", "Cup of coffee", "Bug"},
                2);
    }

    @PostMapping("/api/quiz")
    public QuizResult postAnswer(@RequestParam int answer) {
        return answer == quiz.getAnswer() ? QuizResult.getSuccess() : QuizResult.getFailure();
    }

    @GetMapping("/api/quiz")
    public QuizToSend getQuiz() {
        return new QuizToSend(quiz);
    }
}