package engine.repository.quiz.entity;

import engine.entity.quiz.QuizQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends PagingAndSortingRepository<QuizQuestion, Long> {
  List<QuizQuestion> findAllByQuiz_Id(long id);
}
