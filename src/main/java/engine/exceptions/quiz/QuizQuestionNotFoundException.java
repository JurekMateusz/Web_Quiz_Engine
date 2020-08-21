package engine.exceptions.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "quiz question not found")
public class QuizQuestionNotFoundException extends RuntimeException {}
