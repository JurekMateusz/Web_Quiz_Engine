package engine.services.token;

import engine.entity.token.RefreshToken;
import engine.repository.token.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepo;

  @Autowired
  public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepo) {
    this.refreshTokenRepo = refreshTokenRepo;
  }

  @Override
  public RefreshToken generateRefreshToken() {
    RefreshToken refreshToken =
        RefreshToken.builder()
            .token(UUID.randomUUID().toString())
            .createdDate(Instant.now())
            .build();
    return refreshTokenRepo.save(refreshToken);
  }

  @Override
  public boolean isValid(String token) {
    return refreshTokenRepo.existsByToken(token);
  }

  @Override
  public void deleteRefreshToken(String token) {
    refreshTokenRepo.deleteByToken(token);
  }
}
