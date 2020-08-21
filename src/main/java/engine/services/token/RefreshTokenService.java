package engine.services.token;

import engine.entity.token.RefreshToken;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken();

    boolean isValid(String token);

    void deleteRefreshToken(String token);
}
