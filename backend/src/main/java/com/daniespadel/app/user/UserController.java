package com.daniespadel.app.user;

import com.daniespadel.app.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository users;
  private final PasswordEncoder encoder;

  public UserController(UserRepository users, PasswordEncoder encoder) {
    this.users = users;
    this.encoder = encoder;
  }

  private UserDto toDto(User u) {
    return new UserDto(u.getId(), u.getName(), u.getEmail(), u.getRole().name(), u.getLevel());
  }

  // --- Me ---
  @GetMapping("/me")
  public ResponseEntity<?> me(Principal principal) {
    if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    var u = users.findByEmail(principal.getName()).orElse(null);
    if (u == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","user not found"));
    return ResponseEntity.ok(toDto(u));
  }

  // --- Listado (ADMIN) con búsqueda y paginado simple ---
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Map<String, Object> list(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(required = false) String q
  ) {
    Pageable pageable = PageRequest.of(Math.max(page,0), Math.min(Math.max(size,1), 100));
    var result = (q == null || q.isBlank())
        ? users.findAll(pageable)
        : users.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q, pageable);

    return Map.of(
        "data", result.getContent().stream().map(this::toDto).toList(),
        "meta", Map.of(
            "page", result.getNumber(),
            "size", result.getSize(),
            "totalElements", result.getTotalElements(),
            "totalPages", result.getTotalPages()
        )
    );
  }

  // --- Detalle (ADMIN) ---
  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getOne(@PathVariable UUID id) {
    var u = users.findById(id).orElse(null);
    if (u == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","user not found"));
    return ResponseEntity.ok(toDto(u));
  }

  // --- Crear (ADMIN) ---
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> create(@Valid @RequestBody CreateUserDto in) {
    if (users.existsByEmail(in.email()))
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","email already exists"));

    var u = new User();
    u.setName(in.name());
    u.setEmail(in.email());
    u.setPasswordHash(encoder.encode(in.password()));
    u.setRole(Role.valueOf(in.role().toUpperCase()));
    u.setLevel(in.level() == null ? 0 : in.level());

    var saved = users.save(u);
    return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
  }

  // --- Actualizar (ADMIN) ---
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto in) {
    var u = users.findById(id).orElse(null);
    if (u == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","user not found"));

    if (users.existsByEmailAndIdNot(in.email(), id))
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","email already exists"));

    u.setName(in.name());
    u.setEmail(in.email());
    u.setLevel(in.level() == null ? 0 : in.level());

    var saved = users.save(u);
    return ResponseEntity.ok(toDto(saved));
  }

  // --- Cambiar rol (ADMIN) ---
  @PatchMapping("/{id}/role")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> changeRole(@PathVariable UUID id, @Valid @RequestBody PatchRoleDto in) {
    var u = users.findById(id).orElse(null);
    if (u == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","user not found"));
    u.setRole(Role.valueOf(in.role().toUpperCase()));
    return ResponseEntity.ok(toDto(users.save(u)));
  }

  // --- Cambiar level (ADMIN) ---
  @PatchMapping("/{id}/level")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> changeLevel(@PathVariable UUID id, @Valid @RequestBody PatchLevelDto in) {
    var u = users.findById(id).orElse(null);
    if (u == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","user not found"));
    u.setLevel(in.level() == null ? 0 : in.level());
    return ResponseEntity.ok(toDto(users.save(u)));
  }

  // --- Borrar (ADMIN) ---
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> delete(@PathVariable UUID id) {
    if (!users.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","user not found"));
    users.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
