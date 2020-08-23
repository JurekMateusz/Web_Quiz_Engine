package engine.repository.token;

import engine.entity.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
//    Optional<RefreshToken> findByToken(String token);
    boolean existsByToken(String token);

    void deleteByToken(String token);
}
