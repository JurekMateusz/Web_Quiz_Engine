package engine.dto.converter.user;

import engine.dto.converter.Converter;
import engine.dto.from.user.RegisterUserDto;
import engine.entity.user.User;

public class UserDtoToUserConverter implements Converter<RegisterUserDto, User> {
  @Override
  public User convert(RegisterUserDto userDto) {
    return User.builder().email(userDto.getEmail()).password(userDto.getPassword()).build();
  }
}
