package engine.dto.to.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
  private String authenticationToken;
  private String refreshToken;
  private Instant expiresAt;
  private String username;
}
