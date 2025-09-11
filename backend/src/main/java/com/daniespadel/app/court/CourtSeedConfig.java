package com.daniespadel.app.court;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourtSeedConfig { 
  @Bean(name = "courtSeeder")   
  CommandLineRunner courtSeeder(CourtRepository repo) {
    return args -> {
      for (int i = 1; i <= 4; i++) {
        String name = "Pista " + i;
        if (!repo.existsByName(name)) {
          Court c = new Court();
          c.setName(name);
          c.setStatus(CourtStatus.OPEN);
          repo.save(c);
        }
      }
      System.out.println("Seeded 4 courts.");
    };
  }
}
