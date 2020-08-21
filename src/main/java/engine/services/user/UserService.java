package engine.services.user;

import engine.dto.from.user.AuthUserDto;
import engine.dto.to.auth.AuthenticationResponse;
import engine.dto.from.token.RefreshTokenRequest;
import engine.entity.user.User;

public interface UserService {
    void register(AuthUserDto user);

    User getUserById(long id);

    AuthenticationResponse login(AuthUserDto user);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
