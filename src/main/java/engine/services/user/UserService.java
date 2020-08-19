package engine.services.user;

import engine.dto.from.user.RegisterUserDto;
import engine.entity.user.User;

public interface UserService {
  void register(RegisterUserDto user);

  User getUserById(long id);
}
