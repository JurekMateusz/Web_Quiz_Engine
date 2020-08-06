package engine.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.HashMap;

@RestControllerAdvice
public class ControllerUserValidationHandler {


    private static final String VALIDATION_MESSAGE = "User validation problem";

    @ExceptionHandler(ValidationException.class)
    //todo HttpStatus.NOT_FOUND - change status after pass test JetBrainsAcademy
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = VALIDATION_MESSAGE)
    public HashMap<String, String> handleValidationException(Exception e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", VALIDATION_MESSAGE);
        response.put("error", e.getClass().getSimpleName());
        return response;

    }
}
