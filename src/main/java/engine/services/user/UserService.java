package engine.services.user;

import engine.entity.user.User;

public interface UserService {
  void register(User user);

  User getUserById(long id);
}
