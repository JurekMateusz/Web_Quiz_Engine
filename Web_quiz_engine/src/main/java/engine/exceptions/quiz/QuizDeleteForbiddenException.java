package engine.exceptions.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Try delete quiz that user is not creator")
public class QuizDeleteForbiddenException extends RuntimeException {}
