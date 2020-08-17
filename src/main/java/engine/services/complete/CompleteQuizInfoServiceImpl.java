package engine.services.complete;

import engine.dto.converter.Converter;
import engine.dto.converter.ConverterFactory;
import engine.dto.to.CompleteQuizInfoDto;
import engine.entity.complete.CompleteQuizInfo;
import engine.entity.user.User;
import engine.repository.quiz.CompleteQuizInfoRepository;
import engine.services.user.UserServiceImpl;
import engine.services.utils.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
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
  public Page<CompleteQuizInfoDto> getAll(int pageNo, int pageSize, String sortBy) {
    long id = AuthenticatedUser.getId();
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<CompleteQuizInfo> page = repository.findAllByUserId(id, pageable);
    return convertToDto(page);
  }

  private Page<CompleteQuizInfoDto> convertToDto(Page<CompleteQuizInfo> page) {
    return page.map(
        completeQuizInfo -> {
          Converter<CompleteQuizInfo, CompleteQuizInfoDto> converter =
              ConverterFactory.getConverter(CompleteQuizInfo.class);
          return converter.convert(completeQuizInfo);
        });
  }
}
