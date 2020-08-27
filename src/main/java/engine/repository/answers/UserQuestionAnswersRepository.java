package engine.repository.answers;

import engine.entity.answer.QuestionAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuestionAnswersRepository extends JpaRepository<QuestionAnswers, Long> {
  List<QuestionAnswers> findAllByQuizId(long quizId);

  Optional<QuestionAnswers> findByQuizQuestionId(long questionId);
}
