package engine.services.complete;

import com.sdp.common.assemblers.AssemblerFactory;
import engine.dto.converter.AssemblerWebQuizFactory;
import engine.dto.to.quiz.CompleteQuizInfoDto;
import engine.entity.complete.CompleteQuizInfo;
import engine.entity.user.User;
import engine.repository.quiz.info.CompleteQuizInfoRepository;
import engine.services.user.UserServiceImpl;
import engine.services.utils.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
  public void addIdQuizToUserCompletedQuizzes(long quizId) {
    CompleteQuizInfo quizInfo = createQuizInfo(quizId);
    repository.save(quizInfo);
  }

  private CompleteQuizInfo createQuizInfo(long quizId) {
    CompleteQuizInfo quizInfo = new CompleteQuizInfo();
    User user = userService.getUserById(AuthenticatedUser.getId());
    quizInfo.setUser(user);
    quizInfo.setQuizId(quizId);
    quizInfo.setCompletedAt(LocalDateTime.now());
    return quizInfo;
  }

  @Override
  public Page<CompleteQuizInfoDto> getPage(int pageNo, int pageSize, String sortBy) {
    long id = AuthenticatedUser.getId();
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<CompleteQuizInfo> page = repository.findAllByUserId(id, pageable);
    return convertToDto(page);
  }

  @Override
  public List<CompleteQuizInfo> getAllUserCompletedQuizzes() {
    long id = AuthenticatedUser.getId();
    return repository.findAllByUser_Id(id);
  }

  private Page<CompleteQuizInfoDto> convertToDto(Page<CompleteQuizInfo> page) {
    return page.map(
        completeQuizInfo -> {
          AssemblerFactory<CompleteQuizInfo, CompleteQuizInfoDto> converter =
              AssemblerWebQuizFactory.getConverter(CompleteQuizInfo.class);
          return converter.assemble(completeQuizInfo);
        });
  }
}
