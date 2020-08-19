package engine.controllers.quiz;

import engine.dto.from.quiz.QuizFromUserDto;
import engine.dto.to.QuizToUserDto;

import java.util.Collections;
import java.util.List;

public class QuizTestData {
  public static final String USER_REGISTER_JSON =
      "{\"email\":\"test@gmail.com\",\"password\":\"password\"}";

  public static QuizFromUserDto createQuizDto_1() {
    return QuizFromUserDto.builder()
            .title("Coffee drinks")
            .text("Select only coffee drinks.")
            .options(List.of("Americano", "Cappuccino", "Lemoniade"))
            .answer(List.of(1, 2))
            .build();
  }

  public static QuizToUserDto createQuizCorrectResponse_1() {
    return QuizToUserDto.builder()
            .title("Coffee drinks")
            .text("Select only coffee drinks.")
            .options(new String[]{"Americano", "Cappuccino", "Lemoniade"})
            .build();
  }
  public static QuizFromUserDto createQuizDto_2() {
    return QuizFromUserDto.builder()
            .title("Test")
            .text("test test test ")
            .options(List.of("test1", "test2", "test3"))
            .answer(Collections.emptyList())
            .build();
  }
}
