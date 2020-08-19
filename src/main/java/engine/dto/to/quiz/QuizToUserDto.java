package engine.dto.to.quiz;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class QuizToUserDto {
  private long id;
  private String title;
  private String text;
  private String[] options;
}
