package engine.services.info;

import engine.entity.info.UserHelpQuizInfo;
import engine.repository.info.UserHelpQuizInfoRepository;
import engine.services.utils.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserHelpQuizInfoServiceImpl implements UserHelpQuizInfoService {
  private final UserHelpQuizInfoRepository repository;

  @Autowired
  public UserHelpQuizInfoServiceImpl(UserHelpQuizInfoRepository repository) {
    this.repository = repository;
  }

  @Override
  public void createUserHelpInfo(long userId) {
    UserHelpQuizInfo info = UserHelpQuizInfo.of(userId);
    repository.save(info);
  }

  @Override
  public long getIdLastDoneQuiz() {
    UserHelpQuizInfo info = getInfoAuthenticatedUser();
    return info.getActualQuizId();
  }

  private UserHelpQuizInfo getInfoAuthenticatedUser() {
    long userId = AuthenticatedUser.getId();
    Optional<UserHelpQuizInfo> infoOpt = repository.findById(userId);
    if (infoOpt.isEmpty()) {
      throw new IllegalArgumentException("UserHelpInfo dont exit for authenticated user");
    }
    return infoOpt.get();
  }

  @Override
  @Transactional
  public void updateLastQuizId(long id) {
    UserHelpQuizInfo info = getInfoAuthenticatedUser();
    info.setActualQuizId(id);
    info.setQuizStartedAt(LocalDateTime.now());
  }

  @Override
  public UserHelpQuizInfo getUserQuizInfo() {
    return getInfoAuthenticatedUser();
  }
}
