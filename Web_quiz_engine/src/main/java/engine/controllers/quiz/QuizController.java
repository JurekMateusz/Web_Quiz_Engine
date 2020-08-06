package engine.controllers.quiz;

import engine.dto.QuizFromUser;
import engine.dto.QuizToUser;
import engine.dto.UserAnswer;
import engine.dto.feedback.QuizFeedback;
import engine.services.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/{id}/solve")
    public QuizFeedback checkAnswer(@PathVariable long id, @RequestBody UserAnswer userAnswer) {
        return quizService.checkAnswerById(id, userAnswer);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public QuizToUser addQuiz(@RequestBody @Valid QuizFromUser quizFromUser) {
        return quizService.addQuiz(quizFromUser);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuizToUser getQuiz(@PathVariable long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping
    public List<QuizToUser> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable long id){
        quizService.delete(id);
    }
}