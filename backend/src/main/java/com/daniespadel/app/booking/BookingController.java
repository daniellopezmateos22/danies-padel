package com.daniespadel.app.booking;

import com.daniespadel.app.booking.dto.BookingCreateDto;
import com.daniespadel.app.booking.dto.BookingDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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

  // GET /bookings?date=YYYY-MM-DD[&courtId]
  @GetMapping
  public List<BookingDto> list(@RequestParam String date,
                               @RequestParam(required = false) UUID courtId) {
    var day = LocalDate.parse(date);
    return svc.listByDay(day, courtId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookingDto create(@RequestBody BookingCreateDto in, Authentication auth) {
    UUID userId = userResolver.currentUserId(auth);
    return svc.create(userId, in);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancel(@PathVariable UUID id, Authentication auth) {
    var principal = userResolver.resolve(auth);
    svc.cancel(principal.userId(), id, principal.isAdmin());
  }
}
