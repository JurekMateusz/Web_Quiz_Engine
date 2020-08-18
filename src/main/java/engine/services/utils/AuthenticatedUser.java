package engine.services.utils;

import engine.security.userdetails.UserPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {
  public static long getId() {
    if (!isAuthenticated()) {
      throw new RuntimeException("No Authenticated user");
    }
    return getIdAuthenticatedUser();
  }

  public static boolean isAuthenticated() {
    Authentication authentication = getAuthentication();
    return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
  }

  private static long getIdAuthenticatedUser() {
    Authentication authentication = getAuthentication();
    return ((UserPrincipal) authentication.getPrincipal()).getId();
  }

  private static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
