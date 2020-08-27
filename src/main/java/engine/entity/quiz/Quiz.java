package engine.entity.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "quiz_id")
  private long id;

  private String title;
  private int numbersOfQuestions;

  @Column(name = "user_id", nullable = true)
  private long userId;
}
