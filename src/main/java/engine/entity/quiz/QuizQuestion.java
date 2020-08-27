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
public class QuizQuestion implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "question_id")
  private long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "quiz_id", nullable = true)
  private Quiz quiz;

  private String question;
}
