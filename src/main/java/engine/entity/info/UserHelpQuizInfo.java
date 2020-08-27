package engine.entity.info;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_help_quiz_info")
public class UserHelpQuizInfo {
  @Id
  @Column(name = "user_id")
  private long userId;

  @Column(name = "actual_quiz_id")
  private long actualQuizId;

  private LocalDateTime quizStartedAt;

  private UserHelpQuizInfo(long id) {
    this.userId = id;
  }

  public static UserHelpQuizInfo of(long id) {
    return new UserHelpQuizInfo(id);
  }
}
