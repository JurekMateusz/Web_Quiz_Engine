package engine.dto.converter.complete;

import com.sdp.common.assemblers.Assembler;
import com.sdp.common.assemblers.AssemblerFactory;
import com.sdp.common.assemblers.StandardAssemblerBuilder;
import engine.dto.to.quiz.CompleteQuizInfoDto;
import engine.entity.complete.CompleteQuizInfo;

import javax.validation.constraints.NotNull;

public class CompleteQuizInfoAssemblerFactoryImpl extends AssemblerFactory<CompleteQuizInfo,CompleteQuizInfoDto> {
  @Override
  protected @NotNull Assembler<CompleteQuizInfo, CompleteQuizInfoDto> createAssemblerFactory() {
    return  StandardAssemblerBuilder.create(CompleteQuizInfo.class, CompleteQuizInfoDto.class)
            .from(CompleteQuizInfo::getId)
              .to(CompleteQuizInfoDto::setId)
            .from(CompleteQuizInfo::getCompletedAt)
              .to(CompleteQuizInfoDto::setCompletedAt)
            .build();
    }
}
