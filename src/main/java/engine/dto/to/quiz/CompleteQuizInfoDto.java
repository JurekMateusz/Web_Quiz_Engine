package engine.dto.to.quiz;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompleteQuizInfoDto {
  private long id;
  private LocalDateTime completedAt;

  public static CompleteQuizInfoDto of(long id, LocalDateTime completedAt) {
    return new CompleteQuizInfoDto(id, completedAt);
  }
}
