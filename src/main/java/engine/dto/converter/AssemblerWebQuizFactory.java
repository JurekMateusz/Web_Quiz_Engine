package engine.dto.converter;

import com.google.common.collect.ImmutableMap;
import com.sdp.common.assemblers.AssemblerFactory;
import engine.dto.converter.complete.CompleteQuizInfoAssemblerFactoryImpl;
import engine.dto.converter.quizdto.AddQuizDtoAssemblerFactory;
import engine.dto.converter.quizheader.QuizHeaderDtoAssemblerFactory;
import engine.dto.converter.user.UserDtoToUserConverter;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.from.user.AuthUserDto;
import engine.dto.to.quiz.QuizHeaderDto;
import engine.entity.complete.CompleteQuizInfo;

public class AssemblerWebQuizFactory {
  private static final ImmutableMap<Class<?>, AssemblerFactory> map;

  static {
    map =
        ImmutableMap.<Class<?>, AssemblerFactory>builder()
            .put(AddQuizDto.class, new AddQuizDtoAssemblerFactory())
            .put(CompleteQuizInfo.class, new CompleteQuizInfoAssemblerFactoryImpl())
            .put(AuthUserDto.class, new UserDtoToUserConverter())
            .put(QuizHeaderDto.class, new QuizHeaderDtoAssemblerFactory())
            .build();
  }

  public static AssemblerFactory getConverter(Class<?> myClass) {
    AssemblerFactory result = map.getOrDefault(myClass, null);
    if (result == null) {
      throw new RuntimeException("Converter dont exist: " + myClass.getName());
    }
    return result;
  }
}
