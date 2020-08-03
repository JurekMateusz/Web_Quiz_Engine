package engine.entity.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
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
