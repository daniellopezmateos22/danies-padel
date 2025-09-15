package com.daniespadel.app.booking;

import com.daniespadel.app.user.User;
import com.daniespadel.app.user.Role;
import com.daniespadel.app.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserResolver {

  private final UserRepository userRepository;

  public UserResolver(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public record Principal(UUID userId, boolean isAdmin) {}

  public UUID currentUserId(Authentication auth) {
    String email = auth.getName();
    User u = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + email));
    return u.getId();
  }

  public Principal resolve(Authentication auth) {
    String email = auth.getName();
    User u = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + email));

    boolean isAdmin = (u.getRole() == Role.ADMIN) ||
        auth.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));

    return new Principal(u.getId(), isAdmin);
  }
}
