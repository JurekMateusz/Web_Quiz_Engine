package engine.dto.converter.user;

import com.sdp.common.assemblers.Assembler;
import com.sdp.common.assemblers.AssemblerFactory;
import com.sdp.common.assemblers.StandardAssemblerBuilder;
import engine.dto.from.user.AuthUserDto;
import engine.entity.user.User;

import javax.validation.constraints.NotNull;

public class UserDtoToUserConverter extends AssemblerFactory<AuthUserDto, User> {

  @Override
  protected @NotNull Assembler<AuthUserDto, User> createAssemblerFactory() {
    return StandardAssemblerBuilder.create(AuthUserDto.class, User.class)
        .from(AuthUserDto::getEmail)
        .to(User::setEmail)
        .from(AuthUserDto::getPassword)
        .to(User::setPassword)
        .build();
  }
}
