package engine.controllers.quiz;


import engine.dto.from.quiz.add.AddQuizAnswerQuestionDto;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.from.quiz.add.AddQuizQuestionDto;

import java.util.List;

public class QuizTestData {
  public static final String USER_REGISTER_JSON =
      "{\"email\":\"test@gmail.com\",\"password\":\"password\"}";

  public static AddQuizDto createCorrectQuizDto_1() {

    AddQuizQuestionDto addQuizQuestionDto = AddQuizQuestionDto.builder()
            .question("Select only coffee drinks.")
            .quizAnswerQuestions(
                    List.of(new AddQuizAnswerQuestionDto("Americano",true),
                    new AddQuizAnswerQuestionDto("Cappuccino",true),
                    new AddQuizAnswerQuestionDto("Lemoniade",false)
                            )
            )
            .build();
    return AddQuizDto.builder()
            .title("Coffee drinks")
            .quizQuestions(List.of(addQuizQuestionDto))
            .build();
  }
    public static AddQuizDto createBadSizeQuizDto_1() {

        AddQuizQuestionDto addQuizQuestionDto = AddQuizQuestionDto.builder()
                .question("Select only coffee drinks.")
                .quizAnswerQuestions(
                        List.of(new AddQuizAnswerQuestionDto("Americano",true)
                        )
                )
                .build();
        return AddQuizDto.builder()
                .title("Coffee drinks")
                .quizQuestions(List.of(addQuizQuestionDto))
                .build();
    }
//
//  public static FullQuizToUserDto createQuizCorrectResponse_1() {
//    return FullQuizToUserDto.builder()
//            .title("Coffee drinks")
//            .text("Select only coffee drinks.")
//            .options(new String[]{"Americano", "Cappuccino", "Lemoniade"})
//            .build();
//  }
//  public static QuizFromUserDto createQuizDto_2() {
//    return QuizFromUserDto.builder()
//            .title("Test")
//            .text("test test test ")
//            .options(List.of("test1", "test2", "test3"))
//            .answer(Collections.emptyList())
//            .build();
//  }
}
