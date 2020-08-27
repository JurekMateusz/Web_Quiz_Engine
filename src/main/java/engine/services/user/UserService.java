package engine.services.user;

import engine.dto.from.token.RefreshTokenRequest;
import engine.dto.from.user.AuthUserDto;
import engine.dto.to.auth.AuthenticationResponse;
import engine.entity.user.User;

public interface UserService {
  User register(AuthUserDto user);

  User getUserById(long id);

  AuthenticationResponse login(AuthUserDto user);

  AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
