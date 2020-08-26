package engine.dto.converter.quizheader;

import com.sdp.common.assemblers.Assembler;
import com.sdp.common.assemblers.AssemblerFactory;
import com.sdp.common.assemblers.StandardAssemblerBuilder;
import engine.dto.to.quiz.QuizHeaderDto;
import engine.entity.quiz.Quiz;

import javax.validation.constraints.NotNull;

public class QuizHeaderDtoAssemblerFactory extends AssemblerFactory<Quiz, QuizHeaderDto> {
    @Override
    protected @NotNull Assembler<Quiz, QuizHeaderDto> createAssemblerFactory() {
        return StandardAssemblerBuilder.create(Quiz.class, QuizHeaderDto.class)
                .from(Quiz::getId)
                .to(QuizHeaderDto::setId)
                .from(Quiz::getTitle)
                .to(QuizHeaderDto::setTitle)
                .from(Quiz::getNumbersOfQuestions)
                .to(QuizHeaderDto::setNumberOfQuestions)
                .from(Quiz::getUserId)
                .to(QuizHeaderDto::setUserId)
                .build();
    }
}
