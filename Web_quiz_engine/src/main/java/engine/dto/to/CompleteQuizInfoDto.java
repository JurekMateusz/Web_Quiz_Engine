package engine.dto.to;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CompleteQuizInfoDto {
  private long id;
  private LocalDateTime completedAt;
}
