package com.daniespadel.app.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedAdmin {

  @Bean
  CommandLineRunner seed(UserRepository repo, PasswordEncoder encoder) {
    return args -> {
      String adminEmail = "admin@daniespadel.com";
      if (!repo.existsByEmail(adminEmail)) {
        User u = new User();
        u.setName("Admin");
        u.setEmail(adminEmail);
        u.setPasswordHash(encoder.encode("admin123"));
        u.setRole(Role.ADMIN);
        u.setLevel(5);
        repo.save(u);
        System.out.println("Seeded admin: " + adminEmail + " / admin123");
      }
    };
  }
}
