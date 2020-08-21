package engine.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class LoginFailHandler {
  private static final String VALIDATION_MESSAGE = "Credentials incorrect";

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = VALIDATION_MESSAGE)
  public HashMap<String, String> handleValidationException(Exception e) {
    HashMap<String, String> response = new HashMap<>();
    response.put("message", VALIDATION_MESSAGE);
    return response;
  }
}
