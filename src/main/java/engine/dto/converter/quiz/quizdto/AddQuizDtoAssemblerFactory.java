package engine.dto.converter.quiz.quizdto;

import com.sdp.common.assemblers.Assembler;
import com.sdp.common.assemblers.AssemblerFactory;
import com.sdp.common.assemblers.StandardAssemblerBuilder;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.from.quiz.add.AddQuizQuestionDto;
import engine.entity.quiz.AnswerStatus;
import engine.entity.quiz.Quiz;
import engine.entity.quiz.QuizAnswerQuestion;
import engine.entity.quiz.QuizQuestion;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AddQuizDtoAssemblerFactory extends AssemblerFactory<AddQuizDto, Quiz> {

  public HashMap<Class<?>, List> convert(AddQuizDto addQuizDto) {
    HashMap<Class<?>, List> hashMap = new HashMap<>();
    hashMap.put(QuizAnswerQuestion.class, new ArrayList());

    Quiz quiz = new Quiz();
    quiz.setTitle(addQuizDto.getTitle());

    List<QuizQuestion> quizQuestionList =
        addQuizDto.getQuizQuestions().stream()
            .map(
                addQuizQuestionDto -> {
                  QuizQuestion quizQuestion = new QuizQuestion();
                  quizQuestion.setQuiz(quiz);
                  quizQuestion.setQuestion(addQuizQuestionDto.getQuestion());

                  List<QuizAnswerQuestion> collect =
                      getQuizAnswerQuestions(addQuizQuestionDto, quizQuestion);
                  List list = hashMap.get(QuizAnswerQuestion.class);
                  list.add(collect);
                  return quizQuestion;
                })
            .collect(Collectors.toList());
    hashMap.put(QuizQuestion.class, quizQuestionList);

    return hashMap;
  }

  private List<QuizAnswerQuestion> getQuizAnswerQuestions(
      AddQuizQuestionDto addQuizQuestionDto, QuizQuestion quizQuestion) {
    return addQuizQuestionDto.getQuizAnswerQuestions().stream()
        .map(
            addQuizAnswerQuestionDto -> {
              QuizAnswerQuestion quizAnswerQuestion = new QuizAnswerQuestion();
              quizAnswerQuestion.setAnswer(addQuizAnswerQuestionDto.getAnswer());
              quizAnswerQuestion.setStatus(
                  addQuizAnswerQuestionDto.isCorrect()
                      ? AnswerStatus.CORRECT
                      : AnswerStatus.NOT_CORRECT);
              quizAnswerQuestion.setQuizQuestion(quizQuestion);
              return quizAnswerQuestion;
            })
        .collect(Collectors.toList());
  }

  @Deprecated
  @Override
  protected @NotNull Assembler<AddQuizDto, Quiz> createAssemblerFactory() {
    return StandardAssemblerBuilder.create(AddQuizDto.class, Quiz.class).build();
  }
}
