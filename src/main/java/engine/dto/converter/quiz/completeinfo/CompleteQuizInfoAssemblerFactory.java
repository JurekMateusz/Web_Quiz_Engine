package engine.dto.converter.quiz.completeinfo;

import com.sdp.common.assemblers.Assembler;
import com.sdp.common.assemblers.AssemblerFactory;
import com.sdp.common.assemblers.StandardAssemblerBuilder;
import engine.dto.to.feedback.FeedbackForSolvedQuiz;
import engine.entity.complete.CompleteQuizInfo;

import javax.validation.constraints.NotNull;

public class CompleteQuizInfoAssemblerFactory extends AssemblerFactory<FeedbackForSolvedQuiz, CompleteQuizInfo> {
    @Override
    protected @NotNull Assembler<FeedbackForSolvedQuiz, CompleteQuizInfo> createAssemblerFactory() {
        return StandardAssemblerBuilder.create(FeedbackForSolvedQuiz.class,CompleteQuizInfo.class)
                .from(FeedbackForSolvedQuiz::getStartedAt)
                .to(CompleteQuizInfo::setStartedAt)
                .from(FeedbackForSolvedQuiz::getCompletedAt)
                .to(CompleteQuizInfo::setCompletedAt)
                .from(FeedbackForSolvedQuiz::getCorrectAnswers)
                .to(CompleteQuizInfo::setCorrectAnswers)
                .from(FeedbackForSolvedQuiz::getWrongAnswers)
                .to(CompleteQuizInfo::setWrongAnswers)
                .from(FeedbackForSolvedQuiz::getNumberOfAnswers)
                .to(CompleteQuizInfo::setNumberOfAnswers)
                .from(FeedbackForSolvedQuiz::getNumberOfQuestions)
                .to(CompleteQuizInfo::setNumberOfQuestions)
                .build();

    }
}
