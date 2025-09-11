package com.daniespadel.app.court;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class CourtController {
  private final CourtRepository courts;

  public CourtController(CourtRepository courts) { this.courts = courts; }

  @GetMapping("/courts")
  public Map<String, Object> list() {
    return Map.of("data", courts.findAll());
  }
}
