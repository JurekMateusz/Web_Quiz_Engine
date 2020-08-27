package engine.services.complete;

import com.sdp.common.assemblers.AssemblerFactory;
import engine.dto.to.feedback.FeedbackForSolvedQuiz;
import engine.entity.complete.CompleteQuizInfo;
import engine.repository.quiz.info.CompleteQuizInfoRepository;
import engine.services.user.UserServiceImpl;
import engine.services.utils.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

import static engine.dto.converter.AssemblerWebQuizFactory.getConverter;

@Service
@Component
public class CompleteQuizInfoServiceImpl implements CompleteQuizInfoService {
  private final UserServiceImpl userService;
  private final CompleteQuizInfoRepository repository;

  @Autowired
  public CompleteQuizInfoServiceImpl(
      UserServiceImpl userService, CompleteQuizInfoRepository repository) {
    this.userService = userService;
    this.repository = repository;
  }

  @Override
  public Page<CompleteQuizInfo> getPage(int pageNo, int pageSize, String sortBy) {
    long id = AuthenticatedUser.getId();
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    return repository.findAllByUserId(id, pageable);
  }

  @Override
  public List<CompleteQuizInfo> getAllUserCompletedQuizzes() {
    long id = AuthenticatedUser.getId();
    return repository.findAllByUserId(id);
  }

  @Override
  @Async
  public void setResultToUserStats(long userId, long quizId, FeedbackForSolvedQuiz feedback) {
    CompleteQuizInfo quizInfo = convertToEntity(userId, quizId, feedback);
    repository.save(quizInfo);
  }

  private CompleteQuizInfo convertToEntity(
      long userId, long quizId, FeedbackForSolvedQuiz feedback) {
    AssemblerFactory<FeedbackForSolvedQuiz, CompleteQuizInfo> converter =
        getConverter(FeedbackForSolvedQuiz.class);
    CompleteQuizInfo assemble = converter.assemble(feedback);
    assemble.setQuizId(quizId);
    assemble.setUserId(userId);
    return assemble;
  }
}
