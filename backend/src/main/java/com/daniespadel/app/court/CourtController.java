package com.daniespadel.app.court;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/courts")
public class CourtController {

  private final CourtRepository courts;

  public CourtController(CourtRepository courts) {
    this.courts = courts;
  }

  // LISTA
  @GetMapping
  public Map<String, Object> list() {
    return Map.of("data", courts.findAll());
  }

  // DETALLE (sin líos de genéricos)
  @GetMapping("/{id}")
  public ResponseEntity<?> getOne(@PathVariable UUID id) {
    var opt = courts.findById(id);
    if (opt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of("error", "court not found"));
    }
    return ResponseEntity.ok(opt.get());
  }

  // CREAR (ADMIN)
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> create(@RequestBody Court in) {
    in.setId(null);
    var saved = courts.save(in);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  // ACTUALIZAR (ADMIN)
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Court in) {
    var opt = courts.findById(id);
    if (opt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of("error", "court not found"));
    }
    in.setId(id);
    var saved = courts.save(in);
    return ResponseEntity.ok(saved);
  }

  // BORRAR (ADMIN)
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    courts.deleteById(id);
  }
}
