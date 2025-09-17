package com.daniespadel.app.booking;

import com.daniespadel.app.booking.dto.BookingCreateDto;
import com.daniespadel.app.booking.dto.BookingDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {
  private final BookingService svc;
  private final UserResolver userResolver;

  public BookingController(BookingService svc, UserResolver userResolver) {
    this.svc = svc;
    this.userResolver = userResolver;
  }

  // EXISTENTE: listado por día (opcional court)
  @GetMapping
  public List<BookingDto> list(@RequestParam String date,
                               @RequestParam(required = false) UUID courtId) {
    var day = LocalDate.parse(date);
    return svc.listByDay(day, courtId);
  }

  // NUEVO: detalle
  @GetMapping("/{id}")
  public BookingDto getById(@PathVariable UUID id, Authentication auth) {
    var principal = userResolver.resolve(auth);
    return svc.getByIdVisibleTo(id, principal.userId(), principal.isAdmin())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  // NUEVO: mis reservas (rango opcional)
  @GetMapping("/mine")
  public List<BookingDto> mine(
      Authentication auth,
      @RequestParam(required = false) Optional<LocalDate> from,
      @RequestParam(required = false) Optional<LocalDate> to
  ) {
    UUID me = userResolver.currentUserId(auth);
    return svc.listByUser(me, from, to, false);
  }

  // NUEVO: reservas de un usuario (solo ADMIN)
  @GetMapping("/user/{userId}")
  @PreAuthorize("hasRole('ADMIN')")
  public List<BookingDto> byUser(
      @PathVariable UUID userId,
      @RequestParam(required = false) Optional<LocalDate> from,
      @RequestParam(required = false) Optional<LocalDate> to
  ) {
    return svc.listByUser(userId, from, to, true);
  }

  // EXISTENTE: crear para el usuario autenticado
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookingDto create(@RequestBody BookingCreateDto in, Authentication auth) {
    UUID userId = userResolver.currentUserId(auth);
    return svc.create(userId, in);
  }

  // NUEVO: crear en nombre de otro usuario (solo ADMIN)
  @PostMapping("/admin")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  public BookingDto adminCreate(@RequestParam UUID userId, @RequestBody BookingCreateDto in) {
    return svc.create(userId, in);
  }

  // EXISTENTE: cancelar (BORRAR). Admin ya soportado en el service.
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancel(@PathVariable UUID id, Authentication auth) {
    var principal = userResolver.resolve(auth);
    svc.cancel(principal.userId(), id, principal.isAdmin());
  }
}
