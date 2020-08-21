package engine.repository.quiz.entity;

import engine.entity.quiz.QuizAnswerQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuizAnswerQuestionRepository extends CrudRepository<QuizAnswerQuestion, Long> {
  List<QuizAnswerQuestion> findAllByQuizQuestion_Id(long id);
  void deleteAllByQuizQuestion_Id(long id);
@Query(value = "SELECT id , answer,quizQuestion, status " +
        "FROM quiz_answer_question WHERE quiz_question_id=3 AND status=0",nativeQuery = true)
  List<QuizAnswerQuestion> findAllByQuestion_idAndStatus_Correct(long id);

}
