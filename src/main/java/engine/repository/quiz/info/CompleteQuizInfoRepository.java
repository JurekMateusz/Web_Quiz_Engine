package engine.repository.quiz.info;

import engine.entity.complete.CompleteQuizInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteQuizInfoRepository
    extends PagingAndSortingRepository<CompleteQuizInfo, Long> {
  Page<CompleteQuizInfo> findAllByUserId(long id, Pageable paging);
}
