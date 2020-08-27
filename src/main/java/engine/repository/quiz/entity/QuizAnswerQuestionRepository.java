package engine.repository.quiz.entity;

import engine.entity.quiz.QuizAnswerQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerQuestionRepository extends CrudRepository<QuizAnswerQuestion, Long> {
  List<QuizAnswerQuestion> findAllByQuizQuestion_Id(long id);

  void deleteAllByQuizQuestion_Id(long id);

  @Query(
      value =
          "SELECT answer_id FROM quiz_answer_question " + "WHERE quiz_question_id= ?1 AND status=0",
      nativeQuery = true)
  List<Long> findAllCorrectAnswersIdWhereQuestion_IdIs(long id);
}
