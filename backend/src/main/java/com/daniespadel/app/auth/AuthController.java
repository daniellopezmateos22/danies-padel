package com.daniespadel.app.auth;

import com.daniespadel.app.config.JwtService;
import com.daniespadel.app.user.Role;
import com.daniespadel.app.user.User;
import com.daniespadel.app.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
public class AuthController {

  private final AuthenticationManager authManager;
  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthController(AuthenticationManager authManager, UserRepository users, PasswordEncoder encoder, JwtService jwt) {
    this.authManager = authManager;
    this.users = users;
    this.encoder = encoder;
    this.jwt = jwt;
  }

  @PostMapping("/auth/login")
  public ResponseEntity<?> login(@RequestBody @Valid AuthDtos.LoginRequest req) {
    Authentication auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.email(), req.password()));
    SecurityContextHolder.getContext().setAuthentication(auth);

    User u = users.findByEmail(req.email()).orElseThrow();
    String token = jwt.generate(u.getId(), u.getEmail(), u.getRole().name());

    var summary = new AuthDtos.UserSummary(
        u.getId().toString(), u.getName(), u.getEmail(), u.getRole().name(), u.getLevel());

    return ResponseEntity.ok(new AuthDtos.LoginResponse(token, summary));
  }

  // Registro simple para pruebas locales (puedes desactivar en prod)
  @PostMapping("/auth/register")
  public ResponseEntity<?> register(@RequestBody Map<String, Object> body) {
    String name = (String) body.getOrDefault("name", "User");
    String email = (String) body.get("email");
    String password = (String) body.get("password");
    if (email == null || password == null) return ResponseEntity.badRequest().body(Map.of("error", "email/password required"));
    if (users.existsByEmail(email)) return ResponseEntity.status(409).body(Map.of("error", "email already exists"));

    User u = new User();
    u.setName(name);
    u.setEmail(email);
    u.setPasswordHash(encoder.encode(password));
    u.setRole(Role.PLAYER);
    users.save(u);
    return ResponseEntity.status(201).body(Map.of("userId", u.getId().toString()));
  }

  @GetMapping("/me")
  public ResponseEntity<?> me(Principal principal) {
    if (principal == null) return ResponseEntity.status(401).build();
    var u = users.findByEmail(principal.getName()).orElseThrow();
    return ResponseEntity.ok(Map.of(
        "id", u.getId().toString(),
        "name", u.getName(),
        "email", u.getEmail(),
        "role", u.getRole().name(),
        "level", u.getLevel()
    ));
  }
}
