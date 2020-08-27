package engine.entity.answer;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "_id")
  private long id;

  @Column(name = "answer_id")
  private long answerId;

  @ManyToOne
  @JoinColumn(name = "question_answers_id", nullable = false)
  private QuestionAnswers questionAnswers;

}
