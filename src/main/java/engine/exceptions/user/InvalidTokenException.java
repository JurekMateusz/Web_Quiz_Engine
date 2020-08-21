package engine.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "JWT Token invalid")
public class InvalidTokenException extends RuntimeException{
}
