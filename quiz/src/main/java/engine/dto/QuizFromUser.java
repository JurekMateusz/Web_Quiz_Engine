package engine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuizFromUser {
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @Size(min = 2)
    @NotEmpty
    private List<String> options;
    private List<Integer> answer = new ArrayList<>();
}

