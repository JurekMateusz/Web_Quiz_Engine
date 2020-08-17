package engine.util.quizzes;

public final class QuizzesMapping {
  private static final String BASIC_PATH = "/api";
  public static final String BASIC_QUIZZES_PATH = BASIC_PATH + "/quizzes";
  public static final String CHECK_ANSWER = BASIC_QUIZZES_PATH + "/{id}/solve";
  public static final String QUIZ_BY_ID = BASIC_QUIZZES_PATH + "/{id}";
  public static final String ALL_COMPLETED_QUIZZES = BASIC_QUIZZES_PATH + "/completed";
  public static final String DELETE_QUIZ = BASIC_QUIZZES_PATH + "/{id}";
}
