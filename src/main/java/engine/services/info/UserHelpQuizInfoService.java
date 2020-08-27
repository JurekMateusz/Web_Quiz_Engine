package engine.services.info;

import engine.entity.info.UserHelpQuizInfo;

public interface UserHelpQuizInfoService {
  void createUserHelpInfo(long userId);

  long getIdLastDoneQuiz();

  void updateLastQuizId(long id);

  UserHelpQuizInfo getUserQuizInfo();
}
