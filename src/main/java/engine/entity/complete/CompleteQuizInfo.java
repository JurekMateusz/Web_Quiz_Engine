package engine.entity.complete;

import engine.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CompleteQuizInfo implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "quiz_id", updatable = false, nullable = false)
  private long quizId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private LocalDateTime completedAt;
}
