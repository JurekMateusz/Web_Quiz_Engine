package engine.entity.quiz;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuizOptions {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "option_id")
  private long id;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;

  private String content;
}
