package engine.entity.quiz;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuizOptions implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "option_id")
  private long id;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;

  private String content;
}
