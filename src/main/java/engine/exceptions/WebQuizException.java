package engine.exceptions;

public class WebQuizException extends RuntimeException {
  public WebQuizException(String exMessage, Exception exception) {
    super(exMessage, exception);
  }

  public WebQuizException(String exMessage) {
    super(exMessage);
  }
}
