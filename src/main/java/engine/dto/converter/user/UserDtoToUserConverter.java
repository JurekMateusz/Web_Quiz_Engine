package engine.dto.converter.user;

import engine.dto.converter.Converter;
import engine.dto.from.user.AuthUserDto;
import engine.entity.user.User;

public class UserDtoToUserConverter implements Converter<AuthUserDto, User> {
  @Override
  public User convert(AuthUserDto userDto) {
    return User.builder().email(userDto.getEmail()).password(userDto.getPassword()).build();
  }
}
