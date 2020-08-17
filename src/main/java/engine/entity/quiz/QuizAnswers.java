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
public class QuizAnswers implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "answer_id")
  private long id;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;

  private int answer;
}
