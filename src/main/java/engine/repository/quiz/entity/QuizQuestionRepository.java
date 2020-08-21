package engine.repository.quiz.entity;

import engine.entity.quiz.QuizQuestion;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuizQuestionRepository extends PagingAndSortingRepository<QuizQuestion, Long> {
    List<QuizQuestion> findAllByQuiz_Id(long id);
}
