package engine.dto.to;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizToUserDto {
  private long id;
  private String title;
  private String text;
  private String[] options;
}
